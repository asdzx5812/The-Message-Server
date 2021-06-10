package org.foop.finalproject.theMessageServer;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;

public abstract class GameCard {
    protected GameCardColor color;
    protected String description;
    protected IntelligenceType intelligenceType;

    public abstract void perform(Player target);

    public String getDescription(){
        return description;
    }
    public GameCardColor getColor(){
        return color;
    }

    public IntelligenceType getIntelligenceType() {
        return intelligenceType;
    }
}