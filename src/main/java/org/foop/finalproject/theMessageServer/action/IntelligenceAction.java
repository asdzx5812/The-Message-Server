package org.foop.finalproject.theMessageServer.action;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;

import javax.websocket.EncodeException;
import java.io.IOException;

public class IntelligenceAction extends Action {
    public IntelligenceAction(Game game, Player performer, GameCard card, Player playerTarget) {
        super(game, performer, card, playerTarget);
    }

    public IntelligenceType getType(){ return this.card.getIntelligenceType(); }

    @Override
    public void execute() throws EncodeException, IOException {
        System.out.println("This should not occur since an intelligence will not be executed.");
    }
}
