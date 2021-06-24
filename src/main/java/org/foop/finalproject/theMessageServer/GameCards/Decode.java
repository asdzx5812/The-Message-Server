package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.spec.ECField;

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
    public void perform(Player performer, Player playerTarget, Game game) throws IOException {
        messageService.sendIntelligenceInformationToPlayer(game, playerTarget);
    }
}