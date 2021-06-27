package org.foop.finalproject.theMessageServer.GameCards;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;

import java.util.ArrayList;

public class BurnDown extends GameCard {
    public BurnDown(GameCardColor gameCardColor, IntelligenceType intelligenceType){
        super(gameCardColor, intelligenceType);
        //name = "BURN DOWN"; // 燒毀
        //timingDescription = "You can play this card at anytime.";
        //effectDescription = "Burn down one fake intelligence in front of any player.";
        name = "燒毀"; // 燒毀
        timingDescription = "你可以在功能牌階段使用此牌。";
        effectDescription = "燒燬（任意一位玩家的）一份假情報。";

        needTarget = true;
        // playOnPlayersGameCardRound = true;
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
