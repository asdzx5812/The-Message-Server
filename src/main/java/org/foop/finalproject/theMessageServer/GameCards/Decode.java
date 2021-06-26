package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.action.IntelligenceAction;
import org.foop.finalproject.theMessageServer.round.IntelligenceRound;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.EncodeException;
import java.io.IOException;

public class Decode extends GameCard {
    public Decode(){
        super();
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
        IntelligenceRound intelligenceRound =  (IntelligenceRound) game.getRound().getParentRound();
        GameCard intelligenceCard = intelligenceRound.getIntelligence().getCard();
        messageService.sendIntelligenceInformationToPlayer( intelligenceCard, playerTarget);
    }
}