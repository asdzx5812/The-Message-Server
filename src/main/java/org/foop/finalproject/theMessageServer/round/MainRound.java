package org.foop.finalproject.theMessageServer.round;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.action.IntelligenceAction;

import java.util.ArrayList;

public class MainRound extends Round {
    boolean intelligenceHasSent;

    public MainRound(Player player, Game game) throws Exception {
        super(player, game);
        endPlayer = null;
        name = "Main Round";
        intelligenceHasSent = false;
        onRoundStart();
    }

    @Override
    public void onRoundStart() throws Exception {
        System.out.println("--------遊戲開始--------");
        onTurnStart();

    }

    @Override
    public void onTurnStart() throws Exception{
        // TODO: 廣播誰負責派情報，並且廣播開始功能牌階段
        intelligenceHasSent = false;
        messageService.broadcastTurnStartMessage(game, currentPlayer);
        currentPlayer.drawCards();
        childRound = new GameCardRound(currentPlayer, this);
        game.setRound(childRound);
        childRound.onRoundStart();
    }

    @Override
    public void onTurnProgressing(Action action) throws Exception {
        // TODO: 派情報
        // action為收到的情報
        game.setPassingCard(action.getCard());
        childRound = new IntelligenceRound(currentPlayer, this, (IntelligenceAction)action);
        game.setRound(childRound);
        childRound.onRoundStart();
    }

    @Override
    public void doWhenLeaveChildRound() throws Exception {
        if(childRound instanceof GameCardRound){
            //還有沒有手牌能夠打出當情報
            if(currentPlayer.hasNoHandcard()){
                currentPlayer.lossTheGame();
                onTurnEnd();
            }
            else {
                messageService.informPlayerToSelectAction(game, currentPlayer);
            }
        }else{
            // end Round
            onTurnEnd();
        }
    }

    @Override
    public void onTurnEnd() throws Exception {
        if(satisfyRoundEndCondition()){
            onRoundEnd();
        }
        else {
            currentPlayer = getNextPlayer();
            onTurnStart();
        }
    }

    @Override
    public void onRoundEnd() throws Exception {
        // TODO: Game over
        game.leaveRound();
        // inform a player to play a card
        // GameService -> wake up round.onTurnProgressing

        // gameCard round over: round.onTurnProgressing()
        // Game.over();
    }

    @Override
    public boolean satisfyRoundEndCondition() {
        ArrayList<Player> players = game.getPlayers();
        // check if only same camp player alive (red and blue)
        // ArrayList<Boolean> camps = new ArrayList<>(Collections.nCopies(3, false)); // 0:red 1:blue
        for(Player player: players){
            // camps.set(player.getCamp().type, true);
            if(player.isWin()){
                return true;
            }
        }
        return false;
    }
}
