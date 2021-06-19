package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.GameCards.Counteract;
import org.foop.finalproject.theMessageServer.enums.GameState;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public void onReceiveGameCard(Game game, Action action) throws Exception {

        //若是識破Round
        if(game.getRound().isCounteractRound()){
            //打出的牌是識破
            if (action.getCard() instanceof Counteract){
                game.setCounteractRound(action.getPlayer());
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

    public void onReceivePass(Game game) {
        if(game.getRound().isCounteractRound() &&
                game.getNextPlayer() == game.getRound().getCounteractEnd() ){
            game.finishCounteractRound();
        } else if ( !game.getRound().isCounteractRound() &&
                game.getCurrentPlayer() == game.getCurrentGameCardPlayer()){
            game.finishGameCardRound();
        }

    }
}
