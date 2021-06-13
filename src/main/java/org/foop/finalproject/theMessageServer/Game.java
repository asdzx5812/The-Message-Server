package org.foop.finalproject.theMessageServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import org.foop.finalproject.theMessageServer.enums.Camp;

public class Game implements Runnable {
    private int playerNum;
    protected ArrayList<GameCard> characterCards;
    protected ArrayList<Player> players;

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
        ArrayList<Camp> camps = getInitialCampList();
        Collections.shuffle(users);
        Collections.shuffle(camps);
        for (int i = 0; i < users.size(); i++) {
            players.add(new Player(this, camps.get(i), users.get(i)));
        }
    }

    @Override
    public void run() {
        start();
    }

    public void start() {
        dealInitialCards();
        while (!isGameOver) {
            for (Player player : players) {
                if (!player.isDead()) {
                    player.play();
                }
            }
        }
    }

    public void dealInitialCards() {
        for (Player player : players) {
            player.drawInitialCards();
        }
    }

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
                return getCampByNums(1, 1, 0);
            case 3:
                return getCampByNums(1, 1, 1);
            case 4:
                return getCampByNums(1, 1, 2);
            case 5:
                return getCampByNums(2, 2, 1);
            case 6:
                return getCampByNums(2, 2, 2);
            case 7:
                return getCampByNums(3, 3, 1);
            case 8:
                return getCampByNums(3, 3, 2);
            case 9:
                return getCampByNums(3, 3, 3);
        }
        throw new RuntimeException("PlayerNumError");
    }

    private ArrayList<Camp> getCampByNums(int blueNum, int redNum, int greenNum) {
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
}
