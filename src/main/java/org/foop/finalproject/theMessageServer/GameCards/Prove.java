package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.enums.Camp;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;

import static org.foop.finalproject.theMessageServer.enums.MessageType.BROADCAST_PLAYER_START_SELECTING_GAMECARD_TO_DISCARD;

public class Prove extends GameCard {
    boolean proveType;
    Camp targetCamp;
    public Prove(GameCardColor gameCardColor, IntelligenceType intelligenceType, boolean proveType, Camp targetCamp) {
        super(gameCardColor, intelligenceType);
        // name = "PROVE"; // 試探
        // timingDescription = "Play in playing step of own round.";
        // effectDescription = "Only the player be proved can check the card and take below action according to his identity, and then remove it out of game.";
        name = "試探"; // 試探
        timingDescription = "只能在自己回合使用。面朝下遞給一位玩家。";
        effectDescription = "被試探的玩家必須根據自己的身份作出行動，不可隱瞞。結束後移除這場遊戲。";
        this.proveType = proveType;
        this.targetCamp = targetCamp;
        playOnPlayerTurn = true;
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        // TODO
        if (proveType == false) {
            // type true: draw 2 cards or say 我是臥底

        }
        else{
            // type false: discard 1 card or say 我是好人
            messageService.broadcastPlayerToSelectAction(game, playerTarget, BROADCAST_PLAYER_START_SELECTING_GAMECARD_TO_DISCARD);
        }
    }

    public String getDescriptionAccordingToIdentity(){
        if(proveType){
            return "";
        }else{
            return "";
        }
    }
}