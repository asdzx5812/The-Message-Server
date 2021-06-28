package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;

public class LockOn extends GameCard{
    public LockOn(GameCardColor gameCardColor, IntelligenceType intelligenceType, int order) {
        super(gameCardColor, intelligenceType, order);
        // name = "LOCK ON"; // 鎖定
        // timingDescription = "Play in playing step of own round.";
        // effectDescription = "Another player becomes [Lock On Status] and he can not play [transfer].";
        name = "鎖定"; // 鎖定
        timingDescription = "在自己回合中對（自己以外）任意一位玩家使用";
        effectDescription = "本回合傳出的情報若傳遞到該玩家，則該玩家必須接收。";
        setId(name);
        needTarget = true;
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        playerTarget.changeStatus(PlayerStatus.LockOn);
    }

    @Override
    public String getGameMessage(Player performer, Player playerTarget, Game game) {
        return "";
    }

    @Override
    public boolean isValid(Round currentRound, Player owner) {
        // 傳遞情報前的功能牌階段，當回合派發情報者可以使用
        return currentRound.isGameCardRound() &&
                ( (currentRound.parentRoundIsMainRound() && currentRound.playerIsCurrentPlayerOfParentRound(owner)) ||
                        (currentRound.parentRoundIsIntelligenceRound() && currentRound.playerIsOwnerOfParentRound(owner))
                );
    }
}
