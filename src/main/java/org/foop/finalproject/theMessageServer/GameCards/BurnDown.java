package org.foop.finalproject.theMessageServer.GameCards;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;

import java.util.ArrayList;

public class BurnDown extends GameCard {
    BurnDown(){
        name = "BURN DOWN"; // 燒毀
        timingDescription = "You can play this card at anytime.";
        effectDescription = "Burn down one fake intelligence in front of any player.";
        needTarget = true;
        playOnRoundStart = true;
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
        playOnWhenIntelligencePassingInFrontOfOthers = true;
        playOnWhenIntelligenceSendByPlayer = true;
        playOnWhenIntelligenceSendByOthers = true;
    }
    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        ArrayList<GameCard> blackIntelligences = playerTarget.getIntelligences().get(GameCardColor.BLACK.type);
        if( blackIntelligences.size() > 0 ){
            GameCard lastBlackIntelligence = blackIntelligences.get(blackIntelligences.size()-1);
            blackIntelligences.remove(lastBlackIntelligence);
        }
    }
}
