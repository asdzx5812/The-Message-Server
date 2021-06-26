package org.foop.finalproject.theMessageServer;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import org.foop.finalproject.theMessageServer.service.MessageService;
import org.json.JSONObject;

import javax.websocket.EncodeException;
import java.io.IOException;

public abstract class GameCard {
    protected GameCardColor color;
    protected String name;
    protected String timingDescription;
    protected String effectDescription;
    protected IntelligenceType intelligenceType;
    protected boolean needTarget;
    protected boolean playOnRoundStart;                                     // 燒毀
    protected boolean playWhenOtherCardPlayed;                              // 識破
    protected boolean playOnPlayerTurn;                                     // 試探、真偽莫辨、鎖定
    protected boolean playOnWhenIntelligencePassingInFrontOfOthers;         // 燒毀、鎖定、截獲、退回、調虎離山
    protected boolean playOnWhenIntelligencePassingInFrontOfPlayer;         // 燒毀、鎖定、破譯、調虎離山
    protected boolean playOnWhenIntelligenceSendByPlayer;                   // 燒毀、鎖定、
    protected boolean playOnWhenIntelligenceSendByOthers;                    // 燒毀、鎖定、
    protected MessageService messageService;

    protected GameCard(){
        this.color = GameCardColor.BLACK;
        this.intelligenceType = IntelligenceType.ENCRYPTED_MSG;
        playOnRoundStart = false;
        playOnPlayerTurn = false;
        playWhenOtherCardPlayed = false;
        playOnWhenIntelligencePassingInFrontOfOthers = false;
        playOnWhenIntelligencePassingInFrontOfPlayer = false;
        playOnWhenIntelligenceSendByPlayer = false;
        playOnWhenIntelligenceSendByOthers = false;
    }

    // TODO run validator
    public boolean isValid(){
        return true;
    }
    public abstract void perform(Player performer, Player playerTarget, Game game) throws Exception;
    // 回合開始
    public boolean canPlayOnRoundStart(){ return playOnRoundStart; }
    public boolean canPlayWhenOtherCardPlayed(){ return playWhenOtherCardPlayed; }
    public boolean canPlayOnPlayerTurn(){ return playOnPlayerTurn; }
    public boolean canPlayOnWhenIntelligencePassingInFrontOfOthers(){ return playOnWhenIntelligencePassingInFrontOfOthers; }
    public boolean canPlayOnWhenIntelligencePassingInFrontOfPlayer(){ return playOnWhenIntelligencePassingInFrontOfPlayer; }
    public boolean canPlayOnWhenIntelligenceSendByPlayer(){ return playOnWhenIntelligenceSendByPlayer; }
    public boolean canPlayOnWhenIntelligenceSendByOthers(){ return playOnWhenIntelligenceSendByOthers; }


    public String getTimingDescription(){
        return timingDescription;
    }
    public String getEffectDescription(){
        return effectDescription;
    }
    public boolean getNeedTarget(){return needTarget;}
    public GameCardColor getColor(){
        return color;
    }
    public IntelligenceType getIntelligenceType() {
        return intelligenceType;
    }
    public JSONObject toJsonObject(){
        JSONObject handCardObj = new JSONObject();
        handCardObj.put("name", name);
        handCardObj.put("timingdescription", timingDescription);
        handCardObj.put("effectDescription", effectDescription);

        handCardObj.put("color", color.name);
        handCardObj.put("type", intelligenceType.name);
        handCardObj.put("needTarget", needTarget);
        handCardObj.put("isValid", isValid());
        return handCardObj;
    }
}