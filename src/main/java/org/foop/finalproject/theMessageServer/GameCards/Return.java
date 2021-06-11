package org.foop.finalproject.theMessageServer.GameCards;

import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

public class Return extends GameCard {
    Return(){
        name = "RETURN"; // 退回
        timingDescription = "Play when message comes to you in other players' turn. Player in [Lock On Status] will not affect by this card";
        effectDescription = "The intelligence will be transmitted in another direction.";
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
    }
    @Override
    public void perform(Player target) {

    }
}
