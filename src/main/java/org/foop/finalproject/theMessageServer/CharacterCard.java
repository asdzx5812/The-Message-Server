package org.foop.finalproject.theMessageServer;

public class CharacterCard {
    protected String name;
    protected Gender gender;
    protected Mission mission;
    protected Skill skill;
    protected boolean hidden;

    CharacterCard(String name, Gender gender, Mission mission, Skill skill, boolean hidden){
        this.name = name;
        this.gender = gender;
        this.mission = mission;
        this.skill = skill;
        this.hidden = hidden;
    }

    public String getMissionDescription(){
        if(this.hidden)
            throw new RuntimeException("The information is hiddened.");
        return this.mission.getDescription();
    }
    public String getSkillDescription(){
        if(this.hidden)
            throw new RuntimeException("The information is hiddened.");
        return this.skill.getDescription();
    }
    public boolean isMale(){return this.gender.isMale();}
    public boolean isFemale(){return this.gender.isFemale();}

    protected void Uncover(){
        if(!this.hidden){
            throw new RuntimeException("This card is not covered.");
        }
        this.hidden = false;
    }

    public boolean isWin(){
        return mission.isCompleted();
    }

}

abstract class Mission{
    protected String description;
    abstract protected boolean isCompleted();

    public String getDescription() {
        return description;
    }

}

abstract class Skill{
    String description = "";
    abstract void perform();

    public String getDescription() {
        return description;
    }

}

class Gender{
    private boolean male, female;
    Gender(boolean male, boolean female){
        this.male = male;
        this.female = female;
    }
    
    public boolean isMale(){return male;}
    public boolean isFemale(){return female;}
}