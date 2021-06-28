package org.foop.finalproject.theMessageServer.round;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.action.IntelligenceAction;
import org.foop.finalproject.theMessageServer.action.PassAction;
import org.foop.finalproject.theMessageServer.action.ReceiveAction;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import org.foop.finalproject.theMessageServer.enums.MessageType;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;

import java.util.ArrayList;

public class IntelligenceRound extends Round {
    IntelligenceAction intelligence;
    public IntelligenceRound(Player startPlayer, Round parentRound, IntelligenceAction intelligence) {
        super(startPlayer, parentRound);
        this.intelligence = intelligence;
        this.name = "Intelligence Round";
    }

    public IntelligenceAction getIntelligence() {
        return intelligence;
    }

    public void changeDirection(){ direction *= -1; }

    @Override
    public Player getNextPlayer(){
        //直達
        if(intelligence.getType().equals(IntelligenceType.DIRECT_MSG) ){
            if(currentPlayer == intelligence.getPlayerTarget()){
                return intelligence.getPerformer();
            }
            else{
                return intelligence.getPlayerTarget();
            }
        }//密電 文本
        else{
            int playerId = -1;
            ArrayList<Player> players = game.getPlayers();
            for(int i = 0; i < players.size(); i++){
                if(players.get(i) == currentPlayer){
                    playerId = i;
                    break;
                }
            }
            int nextPlayerId = (playerId + direction + players.size()) % players.size();
            return players.get(nextPlayerId);
        }
    }

    @Override
    public void onRoundStart() {
        this.currentPlayer.beLockOn();
        this.currentPlayer = getNextPlayer();
        //messageService.broadcastRoundStartMessage(game);
        //廣播誰打出情報
        onTurnStart();
    }

    @Override
    public void onTurnStart()  {
        messageService.broadcastPlayerOnIntelligenceInFront(game, currentPlayer);
        childRound = new GameCardRound(currentPlayer, this);
        game.setRound(childRound);
        childRound.onRoundStart();
    }

    @Override
    public void onTurnProgressing(Action action) {
        if(action instanceof ReceiveAction){
            //接收情報
            messageService.broadcastActionBeenPlayedMessage(game, action);
            currentPlayer.receiveIntelligence(intelligence);
            onRoundEnd();
        }
        else if(action instanceof PassAction){
            //Pass情報
            messageService.broadcastActionBeenPlayedMessage(game, action);
            onTurnEnd();
        }
        else{
            System.out.println("Error in IntelligenceRound: get neither ReceiveAction nor PassAction!");
        }
    }

    @Override
    public void doWhenLeaveChildRound() {
        // 詢問接收
        messageService.broadcastPlayerToSelectAction(game, currentPlayer, MessageType.BROADCAST_PLAYER_START_SELECTING_RECEIVE);
    }


    @Override
    public void onTurnEnd() {
        this.currentPlayer = getNextPlayer();
        onTurnStart();
    }

    @Override
    public void onRoundEnd() {
        refreshAllPlayerStatus();
        game.leaveRound();
    }
    private void refreshAllPlayerStatus(){
        for(Player player: game.getPlayers()){
            if(player.getStatus() == PlayerStatus.LockOn || player.getStatus() == PlayerStatus.Trap) {
                player.changeStatusToNormal();
            }
        }
    }
    @Override
    public boolean satisfyRoundEndCondition() {
        return false;
    }
}
