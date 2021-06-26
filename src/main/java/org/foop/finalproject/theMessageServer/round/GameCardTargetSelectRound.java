package org.foop.finalproject.theMessageServer.round;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.action.GameCardAction;

import javax.websocket.EncodeException;
import java.io.IOException;

public class GameCardTargetSelectRound extends Round {
    Action action;
    public GameCardTargetSelectRound(Player startPlayer, Round parentRound, Action action) {
        super(startPlayer, parentRound);
        this.action = action;
        this.name = "GameCard Target Select Round";
    }

    @Override
    public void onRoundStart() {

    }

    @Override
    public void onTurnStart() {

    }

    @Override
    public void onTurnProgressing(Action action)  {
        Player playerTarget = action.getPlayerTarget();
        this.action.setPlayerTarget(playerTarget);
        onTurnEnd();
    }

    @Override
    public void doWhenLeaveChildRound() { }

    @Override
    public void onTurnEnd()  { onRoundEnd(); }

    @Override
    public void onRoundEnd() {
        game.leaveRound();

    }

    @Override
    public boolean satisfyRoundEndCondition() { return true; }
}
