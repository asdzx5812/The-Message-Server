package org.foop.finalproject.theMessageServer;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;

public abstract class GameCard {
    protected GameCardColor color;
    protected String name;
    protected String timingDescription;
    protected String effectDescription;
    protected IntelligenceType intelligenceType;
    protected boolean playOnRoundStart;
    protected boolean playWhenOtherCardPlayed;
    protected boolean playOnOthersTurn;
    protected boolean playOnPlayerTurn;
    protected boolean playOnWhenIntelligencePassingInFrontOfOthers;
    protected boolean playOnWhenIntelligencePassingInFrontOfPlayer;

    protected GameCard(){
        playOnRoundStart = false;
        playOnOthersTurn = false;
        playOnPlayerTurn = false;
        playWhenOtherCardPlayed = false;
        playOnWhenIntelligencePassingInFrontOfOthers = false;
        playOnWhenIntelligencePassingInFrontOfPlayer = false;
    }

    public abstract void perform(Player target);

    public boolean canPlayOnRoundStart(){ return playOnRoundStart; }
    public boolean canPlayWhenOtherCardPlayed(){ return playWhenOtherCardPlayed; }
    public boolean canPlayOnOthersTurn(){ return playOnOthersTurn; }
    public boolean canPlayOnPlayerTurn(){ return playOnPlayerTurn; }
    public boolean canPlayOnWhenIntelligencePassingInFrontOfOthers(){ return playOnWhenIntelligencePassingInFrontOfOthers; }
    public boolean canPlayOnWhenIntelligencePassingInFrontOfPlayer(){ return playOnWhenIntelligencePassingInFrontOfPlayer; }


    public String getTimingDescription(){
        return timingDescription;
    }
    public String getEffectDescription(){
        return effectDescription;
    }
    public GameCardColor getColor(){
        return color;
    }
    public IntelligenceType getIntelligenceType() {
        return intelligenceType;
    }
}