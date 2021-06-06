package org.foop.finalproject.theMessageServer;

import org.foop.finalproject.theMessageServer.enums.Camp;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;

import java.util.ArrayList;

public class Player {
    protected CharacterCard character;
    protected Camp camp;
    protected ArrayList<GameCard> handCards;
    protected ArrayList<ArrayList<GameCard>> intelligence = new ArrayList<>(3); // 0 -> RED, 1 -> BLUE, 2 -> BLACK

    public Player(CharacterCard character, Camp camp, ArrayList<GameCard> handCards) {
        this.character = character;
        this.camp = camp;
        this.handCards = handCards;
    }

    public boolean isWin() {
        if (camp == Camp.RED || camp == Camp.BLUE)
            return intelligence.get(camp.type).size() >= 3;
        else if (camp == Camp.GREEN)
            return character.mission.isCompleted();
        return false;
    }

    public boolean isDead() {
        return intelligence.get(GameCardColor.BLACK.type).size() >= 3;
    }

    public void play() {
        drawCards();
        Game.onRoundStart();
        playGameCards();
        passIntelligence();
        playGameCards();
    }

    public void drawCards() {
        handCards.addAll(Game.drawCards(2));
    }

    public void playGameCards() {
        while (true) {
            // Todo


        }
    }

    public void passIntelligence() { // Todo
        // api
        // Game.passingIntelligence(this, )
    }

    public Action selectAction() { // Todo
        // api
        return null;
    }

    public void onReceiveIntelligence(GameCard intelli) {
        intelligence.get(intelli.color.type).add(intelli);
        if (isDead())
            onDie();
        else if (isWin())
            Game.onGameOver();
    }

    public void onDie() { // Todo
    }
}
