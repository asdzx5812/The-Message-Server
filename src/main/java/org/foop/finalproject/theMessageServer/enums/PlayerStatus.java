package org.foop.finalproject.theMessageServer.enums;

public enum PlayerStatus {
    // refresh when intelligence turn end
    Normal("Normal"), LockOn("Lock On"), Trap("Trap");
    public String state;

    PlayerStatus(String state) {
            this.state = state;
    }

}
