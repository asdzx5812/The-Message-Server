package org.foop.finalproject.theMessageServer.enums;

public enum PlayerStatus {
    Normal("Normal"), LockOn("Lock On"), Trap("Trap");
    public String name;

    PlayerStatus(String name) {
            this.name = name;
    }

}
