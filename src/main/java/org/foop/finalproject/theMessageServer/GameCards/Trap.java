package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;

public class Trap extends GameCard {
    public void Trap() {
        name = "Trap"; // 調虎離山
        timingDescription = "Play when message transmitting.";
        effectDescription = "Another player becomes [Trap Status] (Player in [Lock On Status] cn not be affected by this)";
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
        playOnWhenIntelligencePassingInFrontOfOthers = true;
        playOnWhenIntelligenceSendByPlayer = true;
        playOnWhenIntelligenceSendByOthers = true;
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        playerTarget.changeStatus(PlayerStatus.Trap);
    }

}