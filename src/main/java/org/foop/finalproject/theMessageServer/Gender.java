package org.foop.finalproject.theMessageServer;

public class Gender{
    private boolean male, female;
    public Gender(boolean male, boolean female){
        this.male = male;
        this.female = female;
    }

    public boolean isMale(){return male;}
    public boolean isFemale(){return female;}
}
