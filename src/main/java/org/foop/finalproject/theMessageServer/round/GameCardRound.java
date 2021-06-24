package org.foop.finalproject.theMessageServer.round;

import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.state.Round;

public class GameCardRound extends Round {
    CounteractRound counteractRound;

    GameCardRound(Player endPlayer){
        super(endPlayer);
        counteractRound = null;
    }

    public void setCounteractRound(Player currentPlayer){
        counteractRound = new CounteractRound(currentPlayer, this);
    }
    public CounteractRound getCounteractRound() {
        return counteractRound;
    }
    public void removeCounteractRound(){
        counteractRound = null;
    }

}
