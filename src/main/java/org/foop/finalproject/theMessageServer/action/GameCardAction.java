package org.foop.finalproject.theMessageServer.action;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Action;

import javax.websocket.EncodeException;
import java.io.IOException;

public class GameCardAction extends Action {

    public GameCardAction(Game game, Player performer, GameCard card, Player playerTarget) {
        super(game, performer, card, playerTarget);
        this.card = card;
        this.playerTarget = playerTarget;
    }

    @Override
    public void execute() throws EncodeException, IOException {
        card.perform(performer, playerTarget, game);
    }


}
