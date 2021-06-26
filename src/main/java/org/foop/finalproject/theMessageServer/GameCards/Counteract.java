package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.action.GameCardAction;

public class Counteract extends GameCard {
    public Counteract(){
        super();
        name = "COUNTERACT"; // 識破
        needTarget = false;
        timingDescription = "Play when effect card is used.";
        effectDescription = "Target effect ineffective and put to discard pile.";
        playWhenOtherCardPlayed = true;
    }
    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        GameCardAction action = game.getCurrentActionsOnBoard().pop();

    }
}