package org.foop.finalproject.theMessageServer.action;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Player;
import org.json.JSONObject;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ReceiveAction extends Action {
    public ReceiveAction(Game game, Player performer){
        super(game, performer, null, null);
    }

    @Override
    public void execute() {
        System.out.println("A pass should not be execute, so this should not happen ...");
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playerId", performer.getId());
        jsonObject.put("action", "receive");
        return jsonObject;
    }
}
