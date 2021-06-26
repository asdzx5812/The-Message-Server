package org.foop.finalproject.theMessageServer.enums;

public enum PlayerStatus {
    // refresh when intelligence turn end
    Normal("Normal"),
    LockOn("Lock On"),
    Trap("Trap"),
    Dead("Dead"),
    Lose("lose");


    public String status;
    PlayerStatus(String status) {
            this.status = status;
    }

}
