package org.foop.finalproject.theMessageServer.GameCards;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.round.IntelligenceRound;

public class Return extends GameCard {
    Return(){
        name = "RETURN"; // 退回
        timingDescription = "Play when message comes to you in other players' turn. Player in [Lock On Status] will not affect by this card";
        effectDescription = "The intelligence will be transmitted in another direction.";
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
        playOnWhenIntelligenceSendByOthers = true;
    }
    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        ((IntelligenceRound)game.getRound()).changeDirection();
    }
}
