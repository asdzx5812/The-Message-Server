package org.foop.finalproject.theMessageServer.enums;

public enum Status {

    Normal("Normal"), LockOn("LocKOn"), Trap("Trap");
    public String name;

    Status(String name) {
            this.name = name;
    }

}
