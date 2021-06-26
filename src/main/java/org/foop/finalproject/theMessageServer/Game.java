package org.foop.finalproject.theMessageServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.round.MainRound;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.foop.finalproject.theMessageServer.characters.fakeCharacter;
import org.foop.finalproject.theMessageServer.enums.Camp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.EncodeException;

public class Game{
    @Autowired
    private MessageService messageService;
    private int playerNum;
    protected ArrayList<Character> characterCards;
    protected ArrayList<Player> players;
    protected ArrayList<User> users;
    protected Round round;
    protected Stack<GameCardAction> currentActionsOnBoard;
    protected Stack<GameCard> gameCardsDeck;
    protected ArrayList<GameCard> playedCards;

    protected GameCard passingCard;
    private boolean gameOver = false;
    public boolean isWaitingPlayerAction = false;

    public Game(ArrayList<User> users) {
        this.playerNum = users.size();
        this.users = users;

    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void initializeStage() throws Exception {
        ArrayList<Camp> camps = getInitialCampList();
        Collections.shuffle(users);
        Collections.shuffle(camps);
        for (int i = 0; i < playerNum; i++) {
            Character character = new fakeCharacter();
            players.add(new Player(this, camps.get(i), character, users.get(i)));
            players.get(i).drawInitialCards();
        }

        messageService.broadcastPlayerInformation(this, players);
    }

    public Round getRound(){
        return round;
    }

    public void start() throws Exception {
        messageService.broadCastGameStartMessage(this);
        round = new MainRound(players.get(0), this);
        round.onRoundStart();
    }

    /*
    public void dispatchSelectingActions() {
        for (Player player : players) {
            if (!player.isDead()) {
                player.selectAction();
            }
        }
    }
    */
    public ArrayList<GameCard> drawCards(int num) {
        ArrayList<GameCard> cards = new ArrayList<>();
        if (num >= gameCardsDeck.size()) {
            num -= gameCardsDeck.size();
            for (int i = 0; i < gameCardsDeck.size(); i++) {
                cards.add(gameCardsDeck.pop());
            }
            refreshGameCardsDeck();
        }
        for (int i = 0; i < num; i++) {
            cards.add(gameCardsDeck.pop());
        }
        return cards;
    }

    public void refreshGameCardsDeck() {
        Collections.shuffle(playedCards);
        gameCardsDeck.addAll(playedCards);
        playedCards.clear();
    }

    public void onPlayerDie(Player deadPlayer) {
        // Check green camp win

        // Check red camp win

        // Check blue camp win
    }

    public void onGameOver() {
        gameOver = true;
        messageService.broadcastGameOverMessage(this);
    }

    public Player getPlayerById(String id) {
        for (Player player : players) {
            if (player.id.equals((id))) {
                return player;
            }
        }
        return null;
    }

    private ArrayList<Camp> getInitialCampList() {
        switch (playerNum) {
            case 2:
                return getCampsByNums(1, 1, 0);
            case 3:
                return getCampsByNums(1, 1, 1);
            case 4:
                return getCampsByNums(1, 1, 2);
            case 5:
                return getCampsByNums(2, 2, 1);
            case 6:
                return getCampsByNums(2, 2, 2);
            case 7:
                return getCampsByNums(3, 3, 1);
            case 8:
                return getCampsByNums(3, 3, 2);
            case 9:
                return getCampsByNums(3, 3, 3);
        }
        throw new RuntimeException("PlayerNumError");
    }

    private ArrayList<Camp> getCampsByNums(int blueNum, int redNum, int greenNum) {
        ArrayList<Camp> initialCamp = new ArrayList<>();
        for (int i = 0; i < blueNum; i++) {
            initialCamp.add(Camp.BLUE);
        }
        for (int i = 0; i < redNum; i++) {
            initialCamp.add(Camp.RED);
        }
        for (int i = 0; i < greenNum; i++) {
            initialCamp.add(Camp.GREEN);
        }
        return initialCamp;
    }

    public void placeGameCardActionOnBoard(GameCardAction action){
        this.currentActionsOnBoard.push(action);
        messageService.broadcastActionBeenPlayedMessage(this, action);
    }

    public void dispatchSelectingActions() {
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public void leaveRound() throws Exception {
        if(round instanceof MainRound){
            onGameOver();
        }
        else {
            this.round = this.round.getParentRound();
            round.doWhenLeaveChildRound();
        }
    }

    public Stack<GameCardAction> getCurrentActionsOnBoard() {
        return currentActionsOnBoard;
    }

    public void takeActionOnBoard() throws Exception {
        while(currentActionsOnBoard.size() > 0){
            GameCardAction action = currentActionsOnBoard.pop();
            action.execute();
        }

    }

    public void setPassingCard(GameCard passingCard) {
        this.passingCard = passingCard;
    }
}
