package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

import java.security.spec.ECField;

public class Decode extends GameCard {
    @Override
    public void perform(Player target) {
        name = "Decode";
        timingDescription = "Play when message comes to you.";
        effectDescription = "Check the message.";
    }
}