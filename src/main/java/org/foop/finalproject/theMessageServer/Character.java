package org.foop.finalproject.theMessageServer;

import org.foop.finalproject.theMessageServer.missions.emptyMission;
import org.foop.finalproject.theMessageServer.skills.emptySkill;

public class Character {
    protected String name;
    protected Gender gender;
    protected Mission mission;
    protected Skill skill;
    protected boolean hidden;

    public Character(String name, Gender gender, Mission mission, Skill skill, boolean hidden) {
        this.name = name;
        this.gender = gender;
        this.mission = mission;
        this.skill = skill;
        this.hidden = hidden;
    }



    public String getMissionDescription(){
        if(this.hidden)
            throw new RuntimeException("The information is hidden.");
        return this.mission.getDescription();
    }
    public String getSkillDescription(){
        if(this.hidden)
            throw new RuntimeException("The information is hidden.");
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



