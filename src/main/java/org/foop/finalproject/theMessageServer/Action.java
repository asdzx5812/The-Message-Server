package org.foop.finalproject.theMessageServer;

public class Action {
    private Player performer;
    private GameCard card;
    private Player target;

    public Action(Player performer, GameCard card, Player target) {
        this.performer = performer;
        this.card = card;
        this.target = target;
    }

    public void execute() {
        card.perform(performer, target);
    }

    public GameCard getCard() {
        return card;
    }
    public Player getPlayer(){
        return performer;
    }
}
