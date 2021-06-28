package org.foop.finalproject.theMessageServer.missions;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Mission;
import org.foop.finalproject.theMessageServer.Player;

public class ShanLingMission extends Mission {
    public ShanLingMission(){
        description = "親手讓一位沒有獲得紅藍齊報的玩家死亡。";
    }
    @Override
    protected boolean isCompleted(Game game, Player player) {
        // TODO
        return false;
    }
}
