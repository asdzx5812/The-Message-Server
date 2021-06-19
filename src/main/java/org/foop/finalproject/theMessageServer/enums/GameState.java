package org.foop.finalproject.theMessageServer.enums;

public enum GameState {
    gameStart(0),
    beforePassingIntelligence(1),
    intelligencePassing(2);

    public int type;
    GameState(int type) {
        this.type = type;
    }
}
