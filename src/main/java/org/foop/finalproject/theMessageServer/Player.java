package org.foop.finalproject.theMessageServer;

import org.foop.finalproject.theMessageServer.enums.Camp;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import org.foop.finalproject.theMessageServer.utils.Utility;

import java.util.ArrayList;
import java.util.UUID;

public class Player {
    private Game game;
    protected String id;
    protected CharacterCard character;
    protected Camp camp;
    protected ArrayList<GameCard> handCards;
    protected ArrayList<ArrayList<GameCard>> intelligence = new ArrayList<>(3); // 0 -> RED, 1 -> BLUE, 2 -> BLACK
    protected Action actionToPerform;
    protected User user;

    public Player(Game game, Camp camp, User user) {
        this.game = game;
        this.id = Utility.generatePlayerId();
        this.character = null;
        this.camp = camp;
        this.handCards = new ArrayList<>();
        this.user = user;
    }

    public void setCharacter(CharacterCard character) {
        this.character = character;
    }

    public void removeHandCardByIndex(int idx) {
        handCards.remove(idx);
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
        game.onRoundStart(); // For 燒毀
        playGameCards();
        passIntelligence();
        game.dispatchSelectingActions();
    }

    public void drawInitialCards() {
        handCards.addAll(game.drawCards(3));
        // Todo
        // Notify client

    }

    public void drawCards() {
        handCards.addAll(game.drawCards(2));
        // Todo
        // Notify client

    }

    public void playGameCards() {
        while (true) {
            // Todo: ask client to select
            boolean doAction = false;
            if (!doAction) {
                break;
            }
            game.dispatchSelectingActions();
        }
    }

    public void passIntelligence() { // Todo
        // api
        GameCard intelligenceToPass = null; //Todo
        if (intelligenceToPass.getIntelligenceType() == IntelligenceType.DIRECT_MSG) {
            Player target = null; // Todo: getTarget()
            game.passIntelligence(this, intelligenceToPass, target);
        }
        else {
            game.passIntelligence(this, intelligenceToPass);
        }
    }


    public Action selectAction() { // Todo
        while (game.isWaitingPlayerAction) {
        }
//        removeHandCardByIndex(gameCardIdx);
        return null;
    }

    public void setActionToPerform(int idx, Player target) {
        this.actionToPerform = new Action(handCards.get(idx), target);
    }

    public void onReceivedIntelligence(GameCard intelli) {
        intelligence.get(intelli.color.type).add(intelli);
        if (isDead()) {
            onDie();
        } else if (isWin()) {
            game.onGameOver();
        }
    }

    public void onDie() { // Todo
        game.onPlayerDie(this);
    }

    public boolean onPassedInFront(GameCard intelligence) {
        game.dispatchSelectingActions();
        boolean receiveIntelligence = false;
        if (receiveIntelligence) {
            onReceivedIntelligence(intelligence);
        }
        return receiveIntelligence;
    }

}
