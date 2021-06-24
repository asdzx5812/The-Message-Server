package org.foop.finalproject.theMessageServer.round;

import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.state.Round;

public class CounteractRound extends Round {
    GameCardRound gameCardRound;
    public CounteractRound(Player endPlayer, GameCardRound gameCardRound) {
        super(endPlayer);
        this.gameCardRound = gameCardRound;
    }

    public GameCardRound getGameCardRound(){
        return gameCardRound;
    }
}
