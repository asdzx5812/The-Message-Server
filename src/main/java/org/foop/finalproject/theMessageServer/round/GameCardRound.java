package org.foop.finalproject.theMessageServer.round;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.GameCards.Counteract;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.action.*;

public class GameCardRound extends Round {
    Action currentAction;

    GameCardRound(Player startPlayer, Round round){
        super(startPlayer, round);
        name = "Game Card Round";
    }

    @Override
    public void onRoundStart() throws Exception {
        messageService.broadcastRoundStartMessage(game);
    }

    @Override
    public void onTurnStart() throws Exception{
        messageService.broadcastTurnStartMessage(game, currentPlayer);
        messageService.informPlayerToSelectAction(game, currentPlayer);
    }

    @Override
    public void onTurnProgressing(Action action) throws Exception {
        currentAction = action;
        // When receive a action game card from currentPlayer
        // start a counteract round
        //若打出gamecard
        if(action instanceof GameCardAction){
            endPlayer = currentPlayer;
            //在gamecard round打出識破---報錯
            if(checkIfCounteract(action)){
                System.out.println("In GameCardRound 識破不應該能被打出來");
            }
            else{
                //檢查是不是需要target
                if(((GameCardAction) action).needToChooseTarget()){
                    childRound = new GameCardTargetSelectRound(currentPlayer, this, action);
                    game.setRound(childRound);
                }
                else {
                    //選完target or 一般gamecard 開啟識破round
                    childRound = new CounteractRound(getNextPlayer(), this);
                    game.setRound(childRound);
                    game.placeGameCardActionOnBoard((GameCardAction)action);
                }
                childRound.onRoundStart();
            }
        }//打pass
        else if(action instanceof PassAction){
            onTurnEnd();
        }
        else{
            System.out.println("In GameCardRound send not pass or gamecard!!");
        }
    }

    private boolean checkIfCounteract(Action action) {
        if( action.getCard() instanceof Counteract){
            return true;
        }
        return false;
    }

    @Override
    public void onTurnEnd() throws Exception {
        game.takeActionOnBoard();

        if(satisfyRoundEndCondition()){
            onRoundEnd();
            return;
        }

        currentPlayer = getNextPlayer();

        onTurnStart();
    }

    public void doWhenLeaveChildRound() throws Exception {
        setChildRound(null);
        onTurnEnd();
    }

    @Override
    public void onRoundEnd() throws Exception {
        // round的層級做事
        // 暫時沒事
        // game的層級做事
        game.leaveRound();

    }

    @Override
    public boolean satisfyRoundEndCondition() {
        if(endPlayer == currentPlayer && currentAction instanceof PassAction){
            return true;
        }

        if(endPlayer == getNextPlayer()){
            return true;
        }
        return false;
    }

}
