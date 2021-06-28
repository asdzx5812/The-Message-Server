package org.foop.finalproject.theMessageServer.missions;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Mission;
import org.foop.finalproject.theMessageServer.Player;

public class LaoCiangMission extends Mission {
    public LaoCiangMission(){
        description = "獲得三張或以上的紅色情報。";
    }
    @Override
    protected boolean isCompleted(Game game, Player player) {
        return false;
    }
}
