package org.foop.finalproject.theMessageServer.missions;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Mission;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.enums.Camp;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;

public class SiaoBaiMission extends Mission {

    @Override
    protected boolean isCompleted(Game game, Player player) {
        boolean firstDieFlag = true;
        boolean blueCampAllDieFlag = true;
        boolean redCampAllDieFlag = true;
        if(player.getStatus() == PlayerStatus.Dead){
            for(Player otherPlayer: game.getPlayers()){
                if(otherPlayer == player){
                    continue;
                }
                if(otherPlayer.getStatus() == PlayerStatus.Dead){
                    firstDieFlag = false;
                    break;
                }
            }
        }
        // Check blue camp 全滅
        for(Player otherPlayer: game.getPlayers()){
            if(otherPlayer.getCamp() == Camp.BLUE){
                if(!(otherPlayer.getStatus() == PlayerStatus.Dead || otherPlayer.getStatus() == PlayerStatus.Lose)){
                    blueCampAllDieFlag = false;
                    break;
                }
            }
        }
        //Check all red camp dies
        for(Player otherPlayer: game.getPlayers()){
            if(otherPlayer.getCamp() == Camp.RED){
                if(!(otherPlayer.getStatus() == PlayerStatus.Dead || otherPlayer.getStatus() == PlayerStatus.Lose)){
                    blueCampAllDieFlag = false;
                }
            }
        }
        return firstDieFlag || blueCampAllDieFlag || redCampAllDieFlag;
    }
}
