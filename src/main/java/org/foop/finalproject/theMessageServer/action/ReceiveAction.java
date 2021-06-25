package org.foop.finalproject.theMessageServer.action;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Player;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ReceiveAction extends Action {
    public ReceiveAction(Game game, Player performer){
        super(game, performer, null, null);
    }

    @Override
    public void execute() throws EncodeException, IOException {
        throw new IOException("");
    }

    @Override
    public String toJsonObject() {
        // TODO:
        return null;
    }
}
