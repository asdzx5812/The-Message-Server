package org.foop.finalproject.theMessageServer.action;

import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.GameCards.Prove;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.foop.finalproject.theMessageServer.enums.ProveOption;
import org.json.JSONObject;

public class ProveAction extends Action {

    String choosedOption;
    ProveOption chosenOption;
    public ProveAction(Game game, Player performer,Player beProvedPlayer, GameCard gameCard, String choosedOption) {
        super(game, performer, gameCard, beProvedPlayer);
        //this.gameCardTarget = gameCardTarget;
        this.choosedOption = choosedOption;

    }

    @Override
    public void execute() {
        MessageService messageService = new MessageService();
        switch(chosenOption){
            case DRAW_TWO_CARDS:
                playerTarget.drawCards(2);
                break;
            case THROW_ONE_CARD:
                if(gameCardTarget == null){
                    System.out.println("prove沒選到丟棄手牌");
                    return;
                }
                playerTarget.removeCardFromHandCards(gameCardTarget);
                break;
            case BAD_GUY:
                break;
            case NICE_GUY:
                break;
            default:
                break;
        }
        messageService.broadcastPlayerChooseOptionForProve(game, playerTarget, chosenOption);
    }

    private ProveOption getChosenOption() {
        int proveType = ((Prove)this.card).getProveType();
        int intergerChoosedOption;
        try{
            intergerChoosedOption = Integer.parseInt(choosedOption);
        } catch (Exception e){
            System.out.println("fail to get integer choosedType from " + choosedOption +" .");
            e.printStackTrace();
            return null;
        }
        ProveOption chosenOption = ProveOption.staticFunctions.getOption(proveType, intergerChoosedOption);
        System.out.println(playerTarget.getUser().getName() + chosenOption.chosedOption);

        return chosenOption;

    }

    @Override
    public String getGameMessage(){
        return "";
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("beProvedPlayerId", playerTarget.getId());
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
    public void setChosenOption(String choosedOption){
        this.choosedOption = choosedOption;
        this.chosenOption = getChosenOption();
        return;
    }
    public boolean checkifNeedTarget(){
        switch (this.chosenOption){
            case THROW_ONE_CARD:
                return true;
            default:
                return false;
        }
    }

}
