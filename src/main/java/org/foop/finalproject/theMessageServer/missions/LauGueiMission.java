package org.foop.finalproject.theMessageServer.missions;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Mission;
import org.foop.finalproject.theMessageServer.Player;

public class LauGueiMission extends Mission {
    public LauGueiMission(){
        description = "當你死亡時，展示你的手牌，裡面有四張或以上的紅色卡牌。";

    }
    @Override
    protected boolean isCompleted(Game game, Player player) {
        // int countRedCard = 0;
        // for()
        return false;
    }
}
