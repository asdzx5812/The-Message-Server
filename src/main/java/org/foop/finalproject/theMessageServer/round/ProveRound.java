package org.foop.finalproject.theMessageServer.round;


import org.foop.finalproject.theMessageServer.Action;
import org.foop.finalproject.theMessageServer.GameCards.Counteract;
import org.foop.finalproject.theMessageServer.GameCards.Prove;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.action.ProveAction;
import org.foop.finalproject.theMessageServer.enums.MessageType;
import org.json.JSONObject;

public class ProveRound extends Round {
    Action action;
    public ProveRound(Player creator, Round parentRound, Action action) {
        super(parentRound);
        currentPlayer = creator;
        this.action = action;
        this.name = "Prove Round";
    }

    @Override
    public void onRoundStart() {
        onTurnStart();
    }

    @Override
    public void onTurnStart() {
        JSONObject jsonObject = new JSONObject();
        Prove gameCard = (Prove)this.action.getCard();

        jsonObject.put("camp", gameCard.getTargetCamp());
        //TODO 有角色可以騙人 要判斷
        if(gameCard.getProveType()){
            //抽兩張 or 我是好人
            String[] possibleOptions = {"drawTwoCards", "niceGuy"};
            jsonObject.put("possibleOptions",possibleOptions);
            if(action.getPlayerTarget().getCamp() == gameCard.getTargetCamp()){
                jsonObject.put("shouldBeChoosedOption","drawTwoCards");
            }
            else{
                jsonObject.put("shouldBeChoosedOption","niceGuy");
            }
        }
        else{
            //丟一張 or 我是臥底
            String[] possibleOptions = {"throwOneCard", "badGuy"};
            jsonObject.put("possibleOptions",possibleOptions);
            if(action.getPlayerTarget().getCamp() == gameCard.getTargetCamp()){
                jsonObject.put("shouldBeChoosedOption","throwOneCard");
            }
            else{
                jsonObject.put("shouldBeChoosedOption","badGuy");
            }
        }
        messageService.informPlayerStartSelectActionForProve(currentPlayer, jsonObject);
    }

    @Override
    public void onTurnProgressing(Action action)  {

        //接到player選擇的指令（試探)
        //currentAction
        this.action = action;
        if(((ProveAction)action).getChoosedOption().equals("throwOneCard")){
            System.out.println("需要選擇target 開始targetRound");
            childRound = new TargetSelectRound(currentPlayer, this, action);
            game.setRound(childRound);
            childRound.onRoundStart();
        }
        else{
            onTurnEnd();
        }
    }

    @Override
    public void doWhenLeaveChildRound() {
        onTurnEnd();
    }

    @Override
    public void onTurnEnd()  {

        /*
        if(childRound == null){
            //做除了丟牌的操作

        }
        else if(childRound instanceof TargetSelectRound) {
            //丟牌
            setChildRound(null);
            //
        }
        else{
            System.out.println("In " + name + "no this type round!!!!");
        }
        */
        action.execute();
        System.out.println(name+": doWhenLeaveChild end.");
        onRoundEnd();
    }

    @Override
    public void onRoundEnd() {
        game.leaveRound();
    }

    @Override
    public boolean satisfyRoundEndCondition() { return true; }
}
