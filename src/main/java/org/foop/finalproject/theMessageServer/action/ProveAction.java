package org.foop.finalproject.theMessageServer.action;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.json.JSONObject;

public class ProveAction extends Action {

    String choosedOption;
    public ProveAction(Game game, Player beProvedPlayer, GameCard gameCardTarget, String choosedOption) {
        super(game, beProvedPlayer, null, null);
        this.gameCardTarget = gameCardTarget;
        this.choosedOption = choosedOption;
    }

    @Override
    public void execute() {
        MessageService messageService = new MessageService();
        if(choosedOption.equals("drawTwoCards")){
            performer.drawCards(2);
        }
        else if(choosedOption.equals("throwOneCard")){
            if(gameCardTarget == null){
                System.out.println("prove沒選到丟棄手牌");
                return;
            }
            performer.removeCardFromHandCards(gameCardTarget);
        }
        else if(choosedOption.equals("badGuy")){

        }
        else if(choosedOption.equals("niceGuy")){
        }
        messageService.broadcastPlayerChooseOptionForProve(game, performer, choosedOption);
    }// 我回不去gather town了 .... ???? mac有這麼爛？  我之前那台也是
    // 白畫面你新開一個分業看看 新開一個gather town 分業 gather town 在搞 大千習到google meet了 你剛剛開得好了 原本的好像要他們approve?
    //啥意思 喔我也白畫面 回googlemeet囉 要用哪個
    @Override
    public String getGameMessage(){
        return "";
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("beProvedPlayerId", performer.getId());
        jsonObject.put("action", "prove");
        /*
        jsonObject.put("gameCard", card.toJsonObject());
        if(playerTarget != null){
            jsonObject.put("targetId", playerTarget.getId());
        }*/
        return jsonObject;
    }
    public String getChoosedOption(){
        return this.choosedOption;
    }
}
