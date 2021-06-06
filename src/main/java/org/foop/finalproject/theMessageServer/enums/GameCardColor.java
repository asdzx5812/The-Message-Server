package org.foop.finalproject.theMessageServer.enums;

public enum GameCardColor {
    BLACK(2), RED(0), BLUE(1);
    public int type;
    GameCardColor(int type) {
        this.type = type;
    }
}
