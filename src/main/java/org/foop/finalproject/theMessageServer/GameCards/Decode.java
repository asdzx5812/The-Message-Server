package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

import java.security.spec.ECField;

public class Decode extends GameCard {
    Decode(){
        name = "Decode"; // 破譯
        timingDescription = "Play when message comes to you.";
        effectDescription = "Check the message.";
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
        playOnWhenIntelligenceSendByPlayer = true;
        playOnWhenIntelligenceSendByOthers = true;
    }
    @Override
    public void perform(Player target) {
    }
}