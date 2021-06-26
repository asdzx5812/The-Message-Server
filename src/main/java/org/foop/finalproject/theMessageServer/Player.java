package org.foop.finalproject.theMessageServer;

import org.foop.finalproject.theMessageServer.action.IntelligenceAction;
import org.foop.finalproject.theMessageServer.enums.Camp;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.foop.finalproject.theMessageServer.utils.Utility;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Player {
    private Game game;
    protected String id;
    protected Character character;
    protected Camp camp;
    protected boolean die;
    protected boolean lose;
    @Autowired
    protected MessageService messageService;

    protected PlayerStatus status;
    protected ArrayList<GameCard> handCards;
    protected ArrayList<ArrayList<GameCard>> intelligences = new ArrayList<>(3); // 0 -> RED, 1 -> BLUE, 2 -> BLACK
    protected Action actionToPerform;
    protected User user;

    public Player(Game game, Camp camp, Character character, User user) {
        this.game = game;
        this.id = Utility.generatePlayerId();
        this.character = character;
        this.camp = camp;
        this.status = PlayerStatus.Normal;
        this.handCards = new ArrayList<>();
        this.user = user;
        die = false;
        lose = false;
    }

    public User getUser(){ return user; }

    public String getId(){ return id; }

    public Character getCharacter(){ return character; }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Camp getCamp() {return camp;}

    public int getHandcardsNum(){ return handCards.size();}

    public void removeHandCardByIndex(int idx) {
        handCards.remove(idx);
    }

    public ArrayList<ArrayList<GameCard>> getIntelligences(){
        return intelligences;
    }

    public boolean isWin() {
        if (camp == Camp.RED || camp == Camp.BLUE)
            return intelligences.get(camp.type).size() >= 3;
        else if (camp == Camp.GREEN)
            return character.mission.isCompleted();
        return false;
    }

    public boolean isDead() { return die; }

    public boolean isLosed() { return lose; }

    public void drawInitialCards() { handCards.addAll(game.drawCards(3)); }

    public void drawCards() throws Exception{
        handCards.addAll(game.drawCards(2));
        // Todo
        // Notify client
        messageService.informPlayerCardInformation(game, this);
        messageService.BroadcastPlayerCardNumInformation(game, this);

    }

    public GameCard getCardByIndex(int idx) throws Exception {
        GameCard cardSelected;
        try{
            cardSelected = handCards.get(idx);
        } catch (Exception e) {
            throw new Exception("Index out of range of handcard.");
        }
        return cardSelected;
    }

    public void receiveIntelligence(IntelligenceAction intelligence) {
        GameCard card = intelligence.getCard();
        intelligences.get(card.color.type).add(card);
    }

    public void changeStatus(PlayerStatus newStatus){
        this.status = newStatus;
    }

    public JSONObject toJsonObject(){
        JSONObject playerObj = new JSONObject();
        playerObj.put("userId", user.getId());
        System.out.println("send player information! 1");
        playerObj.put("playerId", getId());
        System.out.println("send player information! 2");
        playerObj.put("name", user.getName());
        System.out.println("send player information! 3");
        playerObj.put("handcardsNum", handCards.size());
        System.out.println("send player information! 4");
        ArrayList<JSONObject> handCardObj = new ArrayList<>();
        for(GameCard gameCard:handCards){
            handCardObj.add(gameCard.toJsonObject());
        }
        System.out.println("send player information! 5");
        playerObj.put("handcards", handCardObj);
        playerObj.put("camp", camp);
        playerObj.put("character", character);
        return playerObj;
    }

    public void lockOn() {
        this.status = PlayerStatus.LockOn;
    }
    public boolean isLockOn(){
        return this.status == PlayerStatus.LockOn? true : false;
    }

    public boolean hasNoHandcard() {
        return handCards.size() > 0;
    }

    public void die() throws Exception{ // Todo
        status = PlayerStatus.Dead;
        messageService.broadcastPlayerStateChangeMessage(game, this);
        game.onPlayerDie(this);
    }

    public void loseTheGame() throws Exception{
        status = PlayerStatus.Lose;
        messageService.broadcastPlayerStateChangeMessage(game, this);
    }
    public PlayerStatus getStatus(){
        return this.status;
    }
    public void changeStatusToNormal() {
        this.status = PlayerStatus.Normal;
    }
}
