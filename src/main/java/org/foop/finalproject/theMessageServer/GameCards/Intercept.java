package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

public class Intercept extends GameCard {
    public Intercept() {
        super();
        name = "INTERCEPT";
        timingDescription = "Play when message transmitting in others round.";
        effectDescription = "Transmitting message comes to you immediately and you become [Lock On Status].";
    }


    @Override
    public void perform(Player target) {

    }
}