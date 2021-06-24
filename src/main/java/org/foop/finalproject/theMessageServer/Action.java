package org.foop.finalproject.theMessageServer;

public class Action {
    private Player performer;
    private GameCard card;
    private Player playerTarget;
    private Game game;

    public Action(Player performer, GameCard card, Player playerTarget, Game game) {
        this.performer = performer;
        this.card = card;
        this.playerTarget = playerTarget;
        this.game = game;
    }

    public void execute() {
        card.perform(performer, playerTarget, game);
    }

    public GameCard getCard() {
        return card;
    }
    public Player getPlayer(){
        return performer;
    }
}
