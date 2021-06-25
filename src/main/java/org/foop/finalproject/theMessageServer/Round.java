package org.foop.finalproject.theMessageServer;

import org.foop.finalproject.theMessageServer.service.GameService;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Round {
    protected Player endPlayer;
    protected Player startPlayer;
    protected Player currentPlayer;
    protected Round parentRound;
    protected Round childRound;
    protected int direction;
    protected Game game;
    protected String name;
    @Autowired
    protected MessageService messageService;
    @Autowired
    protected GameService gameService;
    public Round(Player startPlayer, Game game){
        this.startPlayer = startPlayer;
        this.currentPlayer = startPlayer;
        this.game = game;
        this.parentRound = null;
        this.direction = 1;
    }

    public Round(Player startPlayer, Round parentRound){
        this.startPlayer = startPlayer;
        this.currentPlayer = startPlayer;
        this.game = parentRound.getGame();
        this.parentRound = parentRound;
        this.direction = 1;

    }

    public void setEndPlayer(Player endPlayer) {
        this.endPlayer = endPlayer;
    }

    public Player getEndPlayer(){
        return endPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) { this.currentPlayer = currentPlayer; }

    public Player getCurrentPlayer() { return currentPlayer;}

    public Game getGame() { return game;}

    public Round getParentRound(){return parentRound;}

    public void setChildRound(Round childRound){ this.childRound = childRound; }

    public Player getNextPlayer(){
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

    abstract public void onRoundStart() throws Exception;

    abstract public void onTurnStart() throws Exception;

    abstract public void onTurnProgressing(Action action) throws Exception;

    abstract public void doWhenLeaveChildRound() throws Exception;

    abstract public void onTurnEnd() throws Exception;

    abstract public void onRoundEnd() throws Exception;

    abstract public boolean satisfyRoundEndCondition();

    public String getName(){return name;};
}
