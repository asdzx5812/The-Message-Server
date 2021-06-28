package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;

public class Trap extends GameCard {
    public Trap(GameCardColor gameCardColor, IntelligenceType intelligenceType, int order) {
        super(gameCardColor, intelligenceType, order);
        /// name = "Trap"; // 調虎離山
        // timingDescription = "Play when message transmitting.";
        // effectDescription = "Another player becomes [Trap Status] (Player in [Lock On Status] cn not be affected by this)";
        name = "調虎離山"; // 調虎離山
        timingDescription = "當情報開始傳遞後使用。";
        effectDescription = "指定一位玩家不參與本次的情報傳遞。（不能指定傳出情報者和被鎖定的玩家）";
        setId(name);
        needTarget = true;
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        playerTarget.changeStatus(PlayerStatus.Trap);
    }

    @Override
    public String getGameMessage(Player performer, Player playerTarget, Game game){
        return "";
    }
    @Override
    public boolean isValid(Round currentRound, Player owner){
        return currentRound.isGameCardRound() && currentRound.parentRoundIsIntelligenceRound();
    }
}