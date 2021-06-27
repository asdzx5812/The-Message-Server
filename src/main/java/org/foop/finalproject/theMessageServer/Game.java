package org.foop.finalproject.theMessageServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import org.foop.finalproject.theMessageServer.GameCards.*;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import org.foop.finalproject.theMessageServer.enums.MessageType;
import org.foop.finalproject.theMessageServer.round.MainRound;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.foop.finalproject.theMessageServer.characters.fakeCharacter;
import org.foop.finalproject.theMessageServer.enums.Camp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.EncodeException;

public class Game{

    private MessageService messageService;

    private int playerNum;
    protected ArrayList<Character> characterCards;
    protected ArrayList<Player> players;
    protected ArrayList<User> users;
    protected Round round;
    protected Stack<GameCard> gameCardsDeck;
    protected ArrayList<GameCard> playedCards;
    protected Stack<GameCardAction> currentActionsOnBoard;

    protected GameCard passingCard;
    private boolean gameOver = false;
    public boolean isWaitingPlayerAction = false;

    public Game(ArrayList<User> users) {
        this.playerNum = users.size();
        this.users = users;
        messageService = new MessageService();
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Round getRound(){
        return round;
    }

    public void initializeStage() {
        System.out.println("Initialize Stage start.");
        initializeDeck();
        createDeck();
        initializePlayers();
        System.out.println("Initialize Stage finish.");
        //messageService.broadcastPlayerInformation(this, players);
        //exception above！！
        //System.out.println("Initialize Stage broadcast done.");
    }

    public void initializeDeck(){
        gameCardsDeck = new Stack<>();
        playedCards = new ArrayList<>();
        currentActionsOnBoard = new Stack<>();
    }

    public void createDeck(){
        createGameCard(3, "LockOn", GameCardColor.RED, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(3, "LockOn", GameCardColor.BLUE, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(6, "LockOn", GameCardColor.BLACK, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(2, "Trap", GameCardColor.RED, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(2, "Trap", GameCardColor.BLUE, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(2, "Trap", GameCardColor.BLACK, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(3, "Return", GameCardColor.RED, IntelligenceType.CORPUS_MSG);
        createGameCard(3, "Return", GameCardColor.BLUE, IntelligenceType.CORPUS_MSG);
        createGameCard(2, "Return", GameCardColor.BLACK, IntelligenceType.CORPUS_MSG);
        createGameCard(1, "Intercept", GameCardColor.RED, IntelligenceType.DIRECT_MSG);
        createGameCard(1, "Intercept", GameCardColor.BLUE, IntelligenceType.DIRECT_MSG);
        createGameCard(6, "Intercept", GameCardColor.BLACK, IntelligenceType.DIRECT_MSG);
        createGameCard(2, "Decode", GameCardColor.RED, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(2, "Decode", GameCardColor.BLUE, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(2, "Decode", GameCardColor.BLACK, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(2, "BurnDown", GameCardColor.RED, IntelligenceType.DIRECT_MSG);
        createGameCard(2, "BurnDown", GameCardColor.BLUE, IntelligenceType.DIRECT_MSG);
        createGameCard(2, "BurnDown", GameCardColor.BLACK, IntelligenceType.DIRECT_MSG);
        createGameCard(5, "Counteract", GameCardColor.RED, IntelligenceType.DIRECT_MSG);
        createGameCard(5, "Counteract", GameCardColor.BLUE, IntelligenceType.DIRECT_MSG);
        createGameCard(4, "Counteract", GameCardColor.BLACK, IntelligenceType.DIRECT_MSG);
        createGameCard(6, "Prove", GameCardColor.RED, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(6, "Prove", GameCardColor.BLUE, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(6, "Prove", GameCardColor.BLUE, IntelligenceType.ENCRYPTED_MSG);
        createGameCard(1, "Distribute", GameCardColor.RED, IntelligenceType.CORPUS_MSG);
        createGameCard(1, "Distribute", GameCardColor.BLUE, IntelligenceType.CORPUS_MSG);
        createGameCard(1, "Distribute", GameCardColor.BLACK, IntelligenceType.CORPUS_MSG);
    }

    public void createGameCard(int num, String type, GameCardColor gameCardColor, IntelligenceType intelligenceType){
        for(int i = 0; i < num; i++){
            switch (type){
                case "BurnDown":
                    gameCardsDeck.add(new BurnDown(gameCardColor, intelligenceType));
                    break;
                case "Counteract":
                    gameCardsDeck.add(new Counteract(gameCardColor, intelligenceType));
                    break;
                case "Decode":
                    gameCardsDeck.add(new Decode(gameCardColor, intelligenceType));
                    break;
                case "Distribute":
                    gameCardsDeck.add(new Distribute(gameCardColor, intelligenceType));
                    break;
                case "Intercept":
                    gameCardsDeck.add(new Intercept(gameCardColor, intelligenceType));
                    break;
                case "LockOn":
                    gameCardsDeck.add(new LockOn(gameCardColor, intelligenceType));
                    break;
                case "Prove":
                    gameCardsDeck.add(new Prove(gameCardColor, intelligenceType, false, Camp.BLUE));
                    break;
                case "Return":
                    gameCardsDeck.add(new Return(gameCardColor, intelligenceType));
                    break;
                case "Trap":
                    gameCardsDeck.add(new Trap(gameCardColor, intelligenceType));
                    break;
                default:
                    System.out.println("This should not happen.");
            }
        }
    }

    public void initializePlayers() {
        ArrayList<Camp> camps = getInitialCampList();
        Collections.shuffle(users);
        Collections.shuffle(camps);
        this.players = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            Character character = new fakeCharacter();
            players.add(new Player(this, camps.get(i), character, users.get(i)));
            players.get(i).drawInitialCards();
            //messageService.informPlayerInformation(this, players.get(i));
        }
    }

    public void start() {
        //messageService.broadCastGameStartMessage(this);
        round = new MainRound(players.get(0), this);
        round.onRoundStart();
    }

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

    public void placeGameCardActionOnBoard(GameCardAction action) {
        this.currentActionsOnBoard.push(action);
        messageService.broadcastActionBeenPlayedMessage(this, action, MessageType.BROADCAST_ACTION_BEEN_PLAYED_MESSAGE);
    }

    public void dispatchSelectingActions() {
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public void leaveRound() {
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

    public void takeActionOnBoard() {
        while(currentActionsOnBoard.size() > 0){
            GameCardAction action = currentActionsOnBoard.pop();
            try {
                action.execute();
                //TODO
                //messageService.broadcastActionPerformed(this, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void setPassingCard(GameCard passingCard) {
        this.passingCard = passingCard;
    }
}
