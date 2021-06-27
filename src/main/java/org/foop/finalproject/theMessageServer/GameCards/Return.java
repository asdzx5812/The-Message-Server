package org.foop.finalproject.theMessageServer.GameCards;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import org.foop.finalproject.theMessageServer.round.IntelligenceRound;

public class Return extends GameCard {
    public Return(GameCardColor gameCardColor, IntelligenceType intelligenceType){
        super(gameCardColor, intelligenceType);
        // name = "RETURN"; // 退回
        // timingDescription = "Play when message comes to you in other players' turn. Player in [Lock On Status] will not affect by this card";
        // effectDescription = "The intelligence will be transmitted in another direction.";
        name = "退回"; // 退回
        timingDescription = "當情報傳遞到你的時候使用。(若你已經被鎖定了則退回無法發揮作用)";
        effectDescription = "該情報以反方向傳遞。";
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
        playOnWhenIntelligenceSendByOthers = true;
    }
    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        ((IntelligenceRound)game.getRound()).changeDirection();
    }
}
