package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.action.ProveAction;
import org.foop.finalproject.theMessageServer.enums.*;
import org.foop.finalproject.theMessageServer.round.GameCardRound;
import org.foop.finalproject.theMessageServer.round.ProveRound;
import org.foop.finalproject.theMessageServer.service.JsonService;
import org.json.JSONObject;

import java.util.ArrayList;

public class Prove extends GameCard {
    int proveType;
    Camp targetCamp;
    String[] possibleOptions;
    public Prove(GameCardColor gameCardColor, IntelligenceType intelligenceType, int proveType, Camp targetCamp, int order) {
        super(gameCardColor, intelligenceType, order);
        // name = "PROVE"; // 試探
        // timingDescription = "Play in playing step of own round.";
        // effectDescription = "Only the player be proved can check the card and take below action according to his identity, and then remove it out of game.";
        name = "試探"; // 試探
        timingDescription = "只能在自己回合使用。面朝下遞給一位玩家。";
        effectDescription = "被試探的玩家必須根據自己的身份作出行動，不可隱瞞。結束後移除這場遊戲。";
        setId(name);
        needTarget = true;
        this.proveType = proveType;
        this.targetCamp = targetCamp;
        this.possibleOptions = ProveOption.staticFunctions.getPossibleOptions(proveType);
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        // TODO
        //JsonService jsonService = new JsonService();
        //JSONObject jsonObj = jsonService.getProveObj(this, performer);
        if(playerTarget.isAlive()) {
            ArrayList<String> messages = messageService.getActionMessages(performer.getId(),
                    "的", this.name, "生效了。", playerTarget.getId(), "正在選擇回應。");
            messageService.broadcastActionPerformed(game, messages);

            Round proveRound = new ProveRound(performer, game.getRound(), new ProveAction(game, performer, playerTarget, this, null));
            game.getRound().setChildRound(proveRound);
            game.setRound(proveRound);
            game.getRound().onRoundStart();
        }
        else{
            ArrayList<String> messages = messageService.getActionMessages(performer.getId(),
                    "的", this.name, "沒有生效了，因為", playerTarget.getId(), "已經不在遊戲中了。");
            messageService.broadcastActionPerformed(game, messages);
            playerTarget.beLockOn();
            System.out.println(playerTarget.getUser().getName() + "已經"+ playerTarget.getStatus().status+"了(ERROR)");
        }
        /*
        if (proveType) {
            // type true: draw 2 cards(target camp) or say 我是臥底(other camp)
            // messageService.informPlayerOnProve(playerTarget);
            if(playerTarget.getCamp() == targetCamp){
                playerTarget.drawCards(2);
            }
        }
        else{
            if(playerTarget.getCamp() == targetCamp){
                playerTarget.drawCards(2);
            }
            // type false: discard 1 card(target camp) or say 我是好人(other camp)
            //messageService.informPlayerOnProve(game, playerTarget);
            //messageService.broadcastPlayerToSelectAction(game, playerTarget, MessageType.BROADCAST_PLAYER_START_SELECTING_GAMECARD);
        }
        */
    }

    @Override
    public String getGameMessage(Player performer, Player playerTarget, Game game) {
        return "";
    }

    public String getDescriptionAccordingToIdentity(){
        if(proveType == 0){
            return "";
        }else{
            return "";
        }
    }


    public Camp getTargetCamp(){
        return targetCamp;
    }
    @Override
    public boolean isValid(Round currentRound, Player owner) {
        // 傳遞情報前的功能牌階段，當回合派發情報者可以使用
        return currentRound.isGameCardRound() && currentRound.parentRoundIsMainRound() && currentRound.playerIsCurrentPlayerOfParentRound(owner);
    }
    public int getProveType(){
        return proveType;
    }

    public JSONObject toJsonObjectOnHand(){
        JSONObject handCardObj = new JSONObject();
        handCardObj.put("name", name);
        handCardObj.put("timingDescription", timingDescription);
        String effectDescriptionToSend = this.effectDescription;
        effectDescriptionToSend += "\\n"+targetCamp.name + ":" + possibleOptions[0] + "\\n";
        if(targetCamp != Camp.RED){
            effectDescriptionToSend += Camp.RED.name;
        }
        if(targetCamp != Camp.BLUE){
            effectDescriptionToSend += Camp.BLUE.name;
        }
        if(targetCamp != Camp.GREEN){
            effectDescriptionToSend += Camp.GREEN.name;
        }
        effectDescriptionToSend += ":" + possibleOptions[1];

        handCardObj.put("effectDescription", effectDescriptionToSend);
        handCardObj.put("id",id);
        handCardObj.put("color", color.name.toLowerCase());
        handCardObj.put("type", intelligenceType.name);
        handCardObj.put("needTarget", needTarget);
        return handCardObj;
    }
}