package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;

public class Intercept extends GameCard {
    public Intercept(GameCardColor gameCardColor, IntelligenceType intelligenceType, int order) {
        super(gameCardColor, intelligenceType, order);
        // name = "INTERCEPT"; // 截獲
        // timingDescription = "Play when message transmitting in others round.";
        // effectDescription = "Transmitting message comes to you immediately and you become [Lock On Status].";
        name = "截獲"; // 截獲
        timingDescription = "在他人回合的情報傳遞中使用。 (不受【鎖定】和【調虎離山】的影響，也不受傳遞方式的限制。)";
        effectDescription = "獲得一張傳遞中的情報。";
        setId(name);
    }


    @Override
    public String perform(Player performer, Player playerTarget, Game game) {
        // currentRound -> parentRound : intelligence Round
        game.getRound().getParentRound().setCurrentPlayer(performer);
        messageService.broadcastPlayerOnIntelligenceInFront(game, performer);
    }
    @Override
    public boolean isValid(Round currentRound, Player owner) {
        return currentRound.isGameCardRound() && currentRound.parentRoundIsIntelligenceRound() && !currentRound.playerIsOwnerOfParentRound(owner);
    }
}