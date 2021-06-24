package org.foop.finalproject.theMessageServer.state;
import java.util.Stack;

import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.round.CounteractRound;

public abstract class Round {
    private Player endPlayer;
    private int currentPlayerId;

    public Round(Player endPlayer){
        this.endPlayer = endPlayer;
    }

    public void setEnd(Player endPlayer) {
        this.endPlayer = endPlayer;
    }

    public Player getEndPlayer(){
        return endPlayer;
    }

}
