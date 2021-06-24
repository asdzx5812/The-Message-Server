package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;

public class LockOn extends GameCard{
    public LockOn() {
        super();
        name = "LOCK ON"; // 鎖定
        timingDescription = "Play in playing step of own round.";
        effectDescription = "Another player becomes [Lock On Status] and he can not play [transfer].";
        playOnPlayerTurn = true;
        playOnWhenIntelligencePassingInFrontOfOthers = true;
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
        playOnWhenIntelligenceSendByPlayer = true;
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        playerTarget.changeStatus(PlayerStatus.LockOn);
    }
}
