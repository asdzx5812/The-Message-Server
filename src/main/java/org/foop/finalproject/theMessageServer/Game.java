package org.foop.finalproject.theMessageServer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Game {
    static protected ArrayList<GameCard> characterCards;
    static protected ArrayList<Player> players;

    static protected Stack<GameCard> gameCardsDeck;
    static protected ArrayList<GameCard> playedCards;
    static protected GameCard passingIntelligence;

    static private int passDirection = 1;
    static private boolean isGameOver = false;

    static public void start() {
        while (!isGameOver) {
            for (Player player : players) {
                if (!player.isDead()) {
                    player.play();
                }
            }
        }
    }

    static public void onRoundStart() {
        dispatchSelectingActions();
    }

    static public void dispatchSelectingActions() {
        for (Player player : players) {
            if (!player.isDead()) {
                player.selectAction();
            }
        }
    }


    static public ArrayList<GameCard> drawCards(int num) {
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

    static public void refreshGameCardsDeck() {
        Collections.shuffle(playedCards);
        gameCardsDeck.addAll(playedCards);
        playedCards.clear();
    }

    static public void passIntelligence(Player sender, GameCard intelligence) {
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
            sender.onReceiveIntelligence(intelligence);
        }
    }

    static public void passIntelligence(Player sender, GameCard intelligence, Player target) {
        if (!target.onPassedInFront(intelligence)) {
            sender.onReceiveIntelligence(intelligence);
        }
    }

    static public void onGameCardPlayed() {

    }

    static public void onPlayerDie(Player deadPlayer) {

    }

    static public void onGameOver() {
        isGameOver = true;
        // Todo: cancel all threads
    }
}
