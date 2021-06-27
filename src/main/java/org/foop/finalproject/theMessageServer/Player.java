package org.foop.finalproject.theMessageServer;

import org.foop.finalproject.theMessageServer.action.IntelligenceAction;
import org.foop.finalproject.theMessageServer.enums.Camp;
import org.foop.finalproject.theMessageServer.enums.Gender;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;
import org.foop.finalproject.theMessageServer.service.JsonService;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.foop.finalproject.theMessageServer.utils.Utility;
import org.json.JSONObject;

import java.util.ArrayList;

public class Player {
    private Game game;
    protected String id;
    protected Character character;
    protected Camp camp;
    protected boolean die;
    protected boolean lose;
    protected MessageService messageService;
    protected JsonService jsonService = new JsonService();
    protected PlayerStatus status;
    protected ArrayList<GameCard> handCards;
    protected ArrayList<ArrayList<GameCard>> intelligences; // 0 -> RED, 1 -> BLUE, 2 -> BLACK
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
        this.intelligences = new ArrayList<>();
        for(int i = 0; i < 3; i ++){
            intelligences.add(new ArrayList<>());
        }
        die = false;
        lose = false;
        messageService = new MessageService();
    }

    public User getUser(){ return user; }

    public String getId(){ return id; }

    // public Character getCharacter(){ return character; }

    // public void setCharacter(Character character) {
    //     this.character = character;
    // }

    public Camp getCamp() {return camp;}

    public int getHandcardsNum(){ return handCards.size();}

    public void removeCardFromHandCards(GameCard card) {
        handCards.remove(card);
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

    public boolean isLose() { return lose; }

    public void drawInitialCards() { handCards.addAll(game.drawCards(3)); }

    public void drawCards() {
        System.out.println("Player draw cards start");
        handCards.addAll(game.drawCards(2));
        // Todo
        // Notify client
        messageService.informPlayerCardInformation(game, this);
        messageService.BroadcastPlayerCardNumInformation(game, this);
        System.out.println("Player draw cards end");
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
        playerObj.put("playerId", getId());
        playerObj.put("name", user.getName());
        playerObj.put("handcardsNum", handCards.size());
        playerObj.put("handcards", jsonService.getHandCardsObjs(handCards));
        playerObj.put("intelligences", jsonService.getIntelligencesObjs(intelligences));
        playerObj.put("camp", camp.name);
        playerObj.put("character", character.toJsonObject());
        return playerObj;
    }

    public void beLockOn() {
        this.status = PlayerStatus.LockOn;
    }
    public boolean isLockOn(){
        return this.status == PlayerStatus.LockOn? true : false;
    }
    public void beTrap() { this.status = PlayerStatus.Trap; }
    public boolean isTrapped(){
        return this.status == PlayerStatus.Trap? true : false;
    }

    public boolean hasNoHandcard() {
        return handCards.isEmpty();
    }

    public void die() { // Todo
        status = PlayerStatus.Dead;
        messageService.broadcastPlayerStateChangeMessage(game, this);
        game.onPlayerDie(this);
    }

    public void loseTheGame() {
        status = PlayerStatus.Lose;
        messageService.broadcastPlayerStateChangeMessage(game, this);
    }
    public PlayerStatus getStatus(){
        return this.status;
    }
    public void changeStatusToNormal() {
        this.status = PlayerStatus.Normal;
    }

    // 會進來只會是 GameCard Round 或 Counteract Round or Intelligence Round?
    // 要看parentRound 才知道現在傳情報了沒以及現在的情報是誰傳的
    public ArrayList<String> getValidCardIndices(Game game){
        ArrayList<String> validCardIndices = new ArrayList<>();
        for(GameCard currentCard: handCards){
            if(currentCard.isValid(game.getRound(), this)){
                validCardIndices.add(currentCard.getId());
            }
        }
        return validCardIndices;
    }

    public GameCard getCardById(String gameCardId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.user.getName() + "的目前手牌:");
        for(GameCard gameCard:handCards){
            stringBuilder.append(gameCard.getId() + ",");
        }
        System.out.println(stringBuilder.toString());
        for(GameCard gameCard:handCards){
            if(gameCard.getId().equals(gameCardId)){
                return gameCard;
            }
        }
        return null;
    }

    public ArrayList<String> getValidIntelligence() {
        ArrayList<String> validCardIndices = new ArrayList<>();
        for(GameCard currentCard: handCards){
            validCardIndices.add(currentCard.getId());
        }
        return validCardIndices;
    }
}
