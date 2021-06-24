package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.GameCards.Counteract;
import org.foop.finalproject.theMessageServer.enums.GameState;
import org.foop.finalproject.theMessageServer.round.CounteractRound;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public void onReceiveGameCard(Game game, Action action) throws Exception {

        //若是識破Round
        if(game.getRound() instanceof CounteractRound){
            //打出的牌是識破
            if (action.getCard() instanceof Counteract){
                game.updateCounteractRoundEndPlayer(action.getPlayer());
            }
            else{
                throw new Exception("識破Round打非識破牌");
            }
        }//非識破Round
        else{
            //打出識破牌
            if(action.getCard() instanceof Counteract){
                throw new Exception("非識破Round打識破牌");
            }
            else{
                game.setCurrentGameCardPlayerIndex();
                game.setCounteractRound(action.getPlayer());
            }
        }
        //把牌放到桌上
        game.placeActionOnBoard(action);

    }

    public void onReceiveGameCard(Game game, GameCard gameCard){

    }

    public void onReceivePass(Game game) {

        //Todo 通知下一位打功能牌
        // if CounteractRound -> only can play counteract
        // else -> can play other game card
    }

    public void onTurnEnd(Game game){
        if( game.getNextPlayer() == game.getRound().getEndPlayer() ) { game.finishRound(); return; }
        // startNewTurn
        game.setC
    }

    public void startRound(Game game){ // (有人接收情報後的) 新回合
        // currentPlayer -> currentIntelligencePlayer;
        // inform current player play api
        // 根據api設定?
        // setCurrentGameCardPlayerIndex;
        //
        game.setCurrentGameCardPlayerIndex();
        OnGameCardRound(game);
    }

    public void OnGameCardRound(Game game){  // (可以打功能牌的) 回合
        // TODO: ask game.currentPlayerIndex to play game card
        // if play -> reset currentGameCardPlayerIndex -> start CounteractRound
        // if pass -> if currentPlayer.nextPlayer == currentGameCardPlayerIndex -> endGameCardRound
    }

    public void OnCounteractRound(Game game){ // (有人打識破牌後進入的)回合

    }

}
