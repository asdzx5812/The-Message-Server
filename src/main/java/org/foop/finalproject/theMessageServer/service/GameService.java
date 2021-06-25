package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.GameCards.Counteract;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.round.CounteractRound;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    public void onReceiveAction(Action action) throws Exception{
        action.getGame().getRound().onTurnProgressing(action);
    }
}
