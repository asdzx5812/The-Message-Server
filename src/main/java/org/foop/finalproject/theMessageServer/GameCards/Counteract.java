package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

public class Counteract extends GameCard {
    public void Counteract(){
        name = "COUNTERACT"; // 識破
        timingDescription = "Play when effect card is used.";
        effectDescription = "Target effect ineffective and put to discard pile.";
        playWhenOtherCardPlayed = true;
    }
    @Override
    public void perform(Player target) {
        
    }
}