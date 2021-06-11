package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

public class Trap extends GameCard {
    public void Trap() {
        name = "Trap"; // 調虎離山
        timingDescription = "Play when message transmitting.";
        effectDescription = "Another player becomes [Trap Status] (Player in [Lock On Status] cn not be affected by this)";
        playOnWhenIntelligencePassingInFrontOfOthers = true;
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
    }

    @Override
    public void perform(Player target) {

    }

}