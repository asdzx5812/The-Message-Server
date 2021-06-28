package org.foop.finalproject.theMessageServer.missions;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Mission;
import org.foop.finalproject.theMessageServer.Player;

public class FuSheMission extends Mission {
    public FuSheMission(){
        description = "當一位玩家宣告勝利時，沒有玩家死亡。你的勝利會導致他的失敗。";
    }
    @Override
    protected boolean isCompleted(Game game, Player player) {
        // TODO
        return false;
    }
}
