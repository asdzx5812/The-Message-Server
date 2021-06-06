package org.foop.finalproject.theMessageServer;

public class Action {
    private GameCard card;
    private Player target;

    public Action(GameCard card, Player target) {
        this.card = card;
        this.target = target;
    }

    public void execute() {
        card.perform(target);
    }
}
