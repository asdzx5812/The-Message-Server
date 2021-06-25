package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.round.IntelligenceRound;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.EncodeException;
import java.io.IOException;

public class Decode extends GameCard {
    Decode(){
        name = "Decode"; // 破譯
        timingDescription = "Play when message comes to you.";
        effectDescription = "Check the message.";
        playOnWhenIntelligencePassingInFrontOfPlayer = true;
        playOnWhenIntelligenceSendByPlayer = true;
        playOnWhenIntelligenceSendByOthers = true;
    }
    @Autowired
    private MessageService messageService;

    @Override
    public void perform(Player performer, Player playerTarget, Game game) throws Exception {
        messageService.sendIntelligenceInformationToPlayer( ((IntelligenceRound)game.getRound().getParentRound()).getIntelligence().getCard(), playerTarget);
    }
}