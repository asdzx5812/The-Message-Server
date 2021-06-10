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


    static public boolean isGameOver() {
        for(Player player : players)
            if (player.isWin())
                return true;
        return false;
    }

    static public void start() {
        while (!isGameOver()) {
            for (Player player : players) {
                if (!player.isDead())
                    player.play();
            }
        }
    }

    static public void onRoundStart() {
        dispatchSelectingActions();
    }

    static public void dispatchSelectingActions() {
        for (Player player : players) {
            if (!player.isDead())
                player.selectAction();
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

    public void passIntelligence(Player passingPlayer, GameCard intelligence) {
        passDirection = 1;

    }

    public void onGameCardPlayed() {

    }

    public void onPlayerDie() {

    }

    static public void onGameOver() {

    }
}
