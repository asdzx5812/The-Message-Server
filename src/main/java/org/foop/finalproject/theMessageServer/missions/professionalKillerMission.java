package org.foop.finalproject.theMessageServer.missions;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Mission;
import org.foop.finalproject.theMessageServer.Player;

public class professionalKillerMission extends Mission {
    public professionalKillerMission(){
        description = "親手讓另一位玩家成為第二位或以後死亡的玩家。";
    }
    @Override
    protected boolean isCompleted(Game game, Player player) {
        // TODO
        return false;
    }
}
