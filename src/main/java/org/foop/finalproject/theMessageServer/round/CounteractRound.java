package org.foop.finalproject.theMessageServer.round;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.GameCards.Counteract;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.enums.MessageType;

public class CounteractRound extends Round {
    public CounteractRound(Player endPlayer, GameCardRound gameCardRound) {
        super(endPlayer, gameCardRound);
        name = "Counteract Round";
    }

    @Override
    public void onRoundStart() {
        messageService.broadcastRoundStartMessage(game);
        onTurnStart();
    }

    @Override
    public void onTurnStart() {
        //messageService.broadcastTurnStartMessage(game, currentPlayer);
        messageService.broadcastPlayerToSelectAction(game, currentPlayer, MessageType.BROADCAST_PLAYER_START_SELECTING_GAMECARD);
    }


    @Override
    public void onTurnProgressing(Action action) {
        if(action instanceof GameCardAction){
            if(((GameCardAction)action).getCard() instanceof Counteract){
                game.placeGameCardActionOnBoard((GameCardAction)action);
                endPlayer = currentPlayer;
            }
        }
        onTurnEnd();
    }

    @Override
    public void doWhenLeaveChildRound() {
        System.out.println("In CounteractRound : 識破round不應該有child Round");
    }

    @Override
    public void onTurnEnd()  {
        // 判斷是否結束
        if(satisfyRoundEndCondition()){
            onRoundEnd();
            return;
        }
        // 更新currentPlayer
        currentPlayer = getNextPlayer();
        // start new turn
        onTurnStart();
    }

    @Override
    public void onRoundEnd() {
        game.leaveRound();
    }


    @Override
    public boolean satisfyRoundEndCondition() {
        if(endPlayer == getNextPlayer()) {
            return true;
        }
        return false;
    }
}
