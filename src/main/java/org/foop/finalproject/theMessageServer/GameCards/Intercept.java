package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

public class Intercept extends GameCard {
    public Intercept() {
        super();
        name = "INTERCEPT"; // 截獲
        timingDescription = "Play when message transmitting in others round.";
        effectDescription = "Transmitting message comes to you immediately and you become [Lock On Status].";
        playOnWhenIntelligencePassingInFrontOfOthers = true;
        playOnWhenIntelligenceSendByPlayer = true;
        playOnWhenIntelligenceSendByOthers = true;
    }


    @Override
    public void perform(Player performer, Player playerTarget, Game game) {

    }
}