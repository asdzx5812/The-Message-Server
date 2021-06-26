package org.foop.finalproject.theMessageServer.action;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Action;
import org.json.JSONObject;

import javax.websocket.EncodeException;
import java.io.IOException;

public class GameCardAction extends Action {

    public GameCardAction(Game game, Player performer, GameCard card, Player playerTarget) {
        super(game, performer, card, playerTarget);
        this.card = card;
        this.playerTarget = playerTarget;
    }

    @Override
    public void execute() {
        try {
            card.perform(performer, playerTarget, game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playerId", performer.getId());
        jsonObject.put("action", "playGameCard");
        jsonObject.put("gameCard", card.toJsonObject());
        if(playerTarget != null){
            jsonObject.put("targetId", playerTarget.getId());
        }
        return jsonObject;
    }

}
