package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import org.foop.finalproject.theMessageServer.enums.PlayerStatus;

import java.util.ArrayList;

public class Trap extends GameCard {
    public Trap(GameCardColor gameCardColor, IntelligenceType intelligenceType, int order) {
        super(gameCardColor, intelligenceType, order);
        /// name = "Trap"; // 調虎離山
        // timingDescription = "Play when message transmitting.";
        // effectDescription = "Another player becomes [Trap Status] (Player in [Lock On Status] can not be affected by this)";
        name = "調虎離山"; // 調虎離山
        timingDescription = "當情報開始傳遞後使用。";
        effectDescription = "指定一位自己以外的玩家不參與本次的情報傳遞。（不能指定傳出情報者和被鎖定的玩家）";
        setId(name);
        needTarget = true;
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        if(!playerTarget.isAlive()){
            System.out.println("Should not happen ...");
            String message = "{0} 對已經不在遊戲的 {1} 發動了 {2}，無法發揮作用，";
            ArrayList<String> messages = new ArrayList<>();
            messages.add(performer.getId());
            messages.add("的");
            messages.add(this.name);
            messages.add("無法發揮作用，因為");
            messages.add(playerTarget.getId());
            messages.add("早已不在遊戲中。");
            messages.add("");
            messages.add("");
            messageService.broadcastActionPerformed(game, messages);
        } else if(playerTarget.isLockOn()){
            String message = "{0} 對被鎖定中的 {1} 發動了 {2}，無法發揮作用。";
            ArrayList<String> messages = new ArrayList<>();
            messages.add(performer.getId());
            messages.add("的");
            messages.add(this.name);
            messages.add("無法發揮作用，因為");
            messages.add(playerTarget.getId());
            messages.add("已經被鎖定了。");
            messages.add("");
            messages.add("");
            messageService.broadcastActionPerformed(game, messages);
        } else if(playerTarget.isTrapped()){
            String message = "{0} 對已經被調虎離山的 {1} 發動了 {2}，不會額外發生作用";
            ArrayList<String> messages = new ArrayList<>();
            messages.add(performer.getId());
            messages.add("的");
            messages.add(this.name);
            messages.add("無法發揮作用，因為");
            messages.add(playerTarget.getId());
            messages.add("早已被調虎離山過了。");
            messages.add("");
            messages.add("");
            messageService.broadcastActionPerformed(game, messages);
        }
        else {
            ArrayList<String> messages = new ArrayList<>();
            messages.add(performer.getId());
            messages.add("的");
            messages.add(this.name);
            messages.add("生效了，");
            messages.add(playerTarget.getId());
            messages.add("在本回合不可以接收情報。（情報將不會傳遞到面前）");
            messages.add("");
            messages.add("");
            messageService.broadcastActionPerformed(game, messages);

            playerTarget.beTrap();
        }
    }

    @Override
    public String getGameMessage(Player performer, Player playerTarget, Game game){
        return "";
    }
    @Override
    public boolean isValid(Round currentRound, Player owner){
        return currentRound.isGameCardRound() && currentRound.parentRoundIsIntelligenceRound();
    }
}