package org.foop.finalproject.theMessageServer.round;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.action.ProveAction;

import javax.websocket.EncodeException;
import java.io.IOException;

public class TargetSelectRound extends Round {
    Action action;
    public TargetSelectRound(Player creator, Round parentRound, Action action) {
        super(parentRound);
        currentPlayer = creator;
        this.action = action;
        this.name = "Target Select Round";
    }

    @Override
    public void onRoundStart() {
        onTurnStart();
    }

    @Override
    public void onTurnStart() {
        if(parentRound instanceof ProveRound){
            messageService.informPlayerStartSelectGameCardTarget(game, currentPlayer);
        }
        else {
            messageService.informPlayerStartSelectTarget(game, currentPlayer, action.getCard());
        }
    }

    @Override
    public void onTurnProgressing(Action action)  {
        if(parentRound instanceof ProveRound){
            GameCard gameCardTarget = action.getGameCardTarget();
            this.action.setGameCardTarget(gameCardTarget);
            onTurnEnd();
        }
        else {
            Player playerTarget = action.getPlayerTarget();
            this.action.setPlayerTarget(playerTarget);
            onTurnEnd();
        }

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
