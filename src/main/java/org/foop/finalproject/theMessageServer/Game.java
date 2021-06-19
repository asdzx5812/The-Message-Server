package org.foop.finalproject.theMessageServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import org.foop.finalproject.theMessageServer.enums.Camp;
import org.foop.finalproject.theMessageServer.enums.GameState;
import org.foop.finalproject.theMessageServer.service.GameService;
import org.foop.finalproject.theMessageServer.state.Round;
public class Game{
    private int playerNum;
    private int currentPlayerIndex;
    private int currentIntelligencePlayerIndex;
    private int currentGameCardPlayerIndex;
    protected ArrayList<GameCard> characterCards;
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

    public void start() {
        currentState = GameState.gameStart;
        currentPlayerIndex = 0;
        ArrayList<Camp> camps = getInitialCampList();
        Collections.shuffle(users);
        Collections.shuffle(camps);
        for (int i = 0; i < playerNum; i++) {
            players.add(new Player(this, camps.get(i), users.get(i)));
            players.get(i).drawInitialCards();
        }
        // Todo 選角 and Inform every player their player_id
        for(int i = 0; i < playerNum; i++) {
            // TODO: inform players their player_id to call api
        }
        for(int i = 0; i < playerNum; i++){
            // TODO: inform players to select character
            // TODO: 等到全部人都完成廣播遊戲開始
        }

    }

    public void run(){

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
        round.setCounteractRound(true);
        round.setCounteractEnd(player);
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
        int nextPlayerIndex = (currentPlayerIndex == playerNum)? 0 : currentPlayerIndex + 1;
        return players.get(nextPlayerIndex);
    }

    public void finishCounteractRound() {
        round.setCounteractRound(false);
        round.setCounteractEnd(null);
        takeActionOnBoard();
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
        if (currentState.equals(GameState.beforePassingIntelligence))
    }
}
