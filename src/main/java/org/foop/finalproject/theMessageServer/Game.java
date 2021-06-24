package org.foop.finalproject.theMessageServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import org.foop.finalproject.theMessageServer.GameCards.Counteract;
import org.foop.finalproject.theMessageServer.round.CounteractRound;
import org.foop.finalproject.theMessageServer.round.GameCardRound;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.json.JSONObject;
import org.foop.finalproject.theMessageServer.characterCards.fakeCharacter;
import org.foop.finalproject.theMessageServer.enums.Camp;
import org.foop.finalproject.theMessageServer.enums.GameState;
import org.foop.finalproject.theMessageServer.state.Round;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.EncodeException;

public class Game{
    @Autowired
    private MessageService messageService;
    private int playerNum;
    private int currentPlayerIndex;
    private int currentIntelligencePlayerIndex;
    private int currentGameCardPlayerIndex;
    protected ArrayList<Character> characterCards;
    protected ArrayList<Player> players;
    protected ArrayList<User> users;
    //Todo state
    protected GameState currentState;
    protected Round round;
    protected Stack<Action> currentActionsOnBoard;

    protected Stack<GameCard> gameCardsDeck;
    protected ArrayList<GameCard> playedCards;
    protected GameCard passingIntelligence;

    private int passDirection = 1;
    private boolean isGameOver = false;
    public boolean isWaitingPlayerAction = false;

    public GameCard getPassingIntelligence() {
        return passingIntelligence;
    }

    public Game(ArrayList<User> users) {
        this.playerNum = users.size();
        this.users = users;

    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void initializeStage() throws EncodeException, IOException {
        currentState = GameState.beforePassingIntelligence;
        currentPlayerIndex = 0;
        ArrayList<Camp> camps = getInitialCampList();
        Collections.shuffle(users);
        Collections.shuffle(camps);
        for (int i = 0; i < playerNum; i++) {
            Character character = new fakeCharacter();
            players.add(new Player(this, camps.get(i), character, users.get(i)));
            players.get(i).drawInitialCards();
        }

        JSONObject playersInformationObj = new JSONObject();
        ArrayList<JSONObject> playersObj = new ArrayList<>();
        for(int i = 0; i < playerNum; i++) {
            JSONObject playerObj = players.get(i).toJsonObject();
            playersObj.add(playerObj);
        }
        playersInformationObj.put("players", playersObj);
        messageService.broadcastPlayerInformation(this, playersInformationObj);
    }

    public void start(){

    }

    public GameState getState(){
        return currentState;
    }
    public void setState(GameState newGameState){
        currentState = newGameState;
    }
    public Round getRound(){
        return round;
    }
    /*
    public void onRoundStart() {
        dispatchSelectingActions();
    }

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

    public void passIntelligence(Player sender, GameCard intelligence) {
        passDirection = 1;
        int senderIndex = players.indexOf(sender);
        int currentPlayerIndex = (senderIndex + passDirection) % players.size();
        while (currentPlayerIndex != senderIndex) {
            Player currentPlayer = players.get(currentPlayerIndex);
            if (!currentPlayer.isDead()) {
                if (currentPlayer.onPassedInFront(intelligence)) {
                    break;
                }
            }
            currentPlayerIndex = (currentPlayerIndex + passDirection) % players.size();
        }
        // The intelligence passed back to the sender
        if (currentPlayerIndex == senderIndex) {
            sender.onReceivedIntelligence(intelligence);
        }
    }

    public void passIntelligence(Player sender, GameCard intelligence, Player target) {
        if (!target.onPassedInFront(intelligence)) {
            sender.onReceivedIntelligence(intelligence);
        }
    }

    public void onGameCardPlayed() {

    }

    public void onPlayerDie(Player deadPlayer) {
        // Check green camp win

        // Check red camp win

        // Check blue camp win
    }

    public void onGameOver() {
        isGameOver = true;
        // Todo: cancel all threads
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

    public void placeActionOnBoard(Action action){
        this.currentActionsOnBoard.push(action);
    }

    public void setCounteractRound(Player player) {
        ((GameCardRound)round).setCounteractRound(player);
        round = ((GameCardRound)round).getCounteractRound();
    }

    public void setCurrentGameCardPlayerIndex() {
        currentGameCardPlayerIndex = currentPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getCurrentGameCardPlayer() {
        return players.get(currentGameCardPlayerIndex);
    }

    public Player getNextPlayer() {
        int nextPlayerIndex = (currentPlayerIndex == playerNum-1)? 0 : currentPlayerIndex + 1;
        return players.get(nextPlayerIndex);
    }

    public void finishRound() {
        if(round instanceof CounteractRound){
            round = ((CounteractRound) round).getGameCardRound();
            ((GameCardRound) round).removeCounteractRound();
            // TODO: currentPlayerIndex = round.getCurrentPlayer();
        } else {

        }
        // takeActionOnBoard();
    }
    private void takeActionOnBoard(){
        for(Action action: currentActionsOnBoard){
            playedCards.add(action.getCard());
        }
        if(currentActionsOnBoard.size() % 2 == 1) {
            currentActionsOnBoard.get(0).execute();
        }
        currentActionsOnBoard.clear();
    }
    public void finishGameCardRound() {
        // TODO
        if (currentState.equals(GameState.beforePassingIntelligence)){
            // TODO: ask player to pass intelligence ( if player's handcard == null, he/she will loss the game.
        } else if(currentState.equals(GameState.intelligencePassing)){
            // TODO: ask current player to receive or not
            // if receive -> receive
            // else -> pass the intelligence to the next player and start new game card round
        }
    }

    public void onReceiveIntelligence(Player player) {
    }

    public void dispatchSelectingActions() {
    }

    public void onRoundStart() {
        // for()
    }

    public void changePassingDirection(){
        passDirection *= -1;
    }

    public void updateCounteractRoundEndPlayer(Player player) {
        round.setEnd(player);
    }
}
