package org.foop.finalproject.theMessageServer.enums;

public enum GameCardColor {
    BLACK(2), RED(0), BLUE(1);
    public int type;
    GameCardColor(int type) {
        this.type = type;
    }

    @Override
    public String toString(){
        if(type == 0)
            return "Red";
        else if(type == 1)
            return "Blue";
        else
            return "Black";
    }
}
