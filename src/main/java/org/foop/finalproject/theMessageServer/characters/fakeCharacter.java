package org.foop.finalproject.theMessageServer.characters;

import org.foop.finalproject.theMessageServer.Character;
import org.foop.finalproject.theMessageServer.Gender;
import org.foop.finalproject.theMessageServer.missions.emptyMission;
import org.foop.finalproject.theMessageServer.skills.emptySkill;

public class fakeCharacter extends Character {
    public fakeCharacter(){
        super(  "fake guy",
                new Gender(true, false),
                new emptyMission(),
                new emptySkill(),
                false
        );
    }
}
