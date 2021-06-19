package org.foop.finalproject.theMessageServer.state;
import java.util.Stack;

import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Action;
public class Round {
    private boolean counteractRound;
    private Player counteractEnd;

    Round(Player counteractEnd){
        this.counteractEnd = counteractEnd;
    }
    public void setCounteractEnd(Player counteractEnd) {
        counteractEnd = counteractEnd;
    }

    public void setCounteractRound(boolean counteractRound) {
        counteractRound = counteractRound;
    }

    public Player getCounteractEnd(){
        return counteractEnd;
    }
    public boolean isCounteractRound(){
        return counteractRound;
    }
}
