package org.foop.finalproject.theMessageServer.enums;

public enum PlayerStatus {
    // TODO: refresh when intelligence turn end
    Normal("Normal"),
    LockOn("LockOn"),
    Trap("Trap"),
    Dead("Dead"),
    Lose("lose");


    public String status;
    PlayerStatus(String status) {
            this.status = status;
    }

}
