package org.foop.finalproject.theMessageServer;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;

public abstract class GameCard {
    protected GameCardColor color;
    protected String description;

    public abstract void perform(Player target);

    public String getDescription(){
        return description;
    }
    public GameCardColor getColor(){
        return color;
    }
}