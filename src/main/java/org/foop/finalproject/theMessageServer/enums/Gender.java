package org.foop.finalproject.theMessageServer.enums;

public enum Gender {
    MAN(true, false),
    WOMAN(false, true),
    BOTH(true, true),
    NONE(true, true);

    boolean male;
    boolean female;

    Gender(boolean male, boolean female) {
        this.male = male;
        this.female = female;
    }

    public boolean isMale(){
        return male;
    }
    public boolean isFemale(){
        return female;
    }
}
