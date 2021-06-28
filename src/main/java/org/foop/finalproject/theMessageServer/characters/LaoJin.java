package org.foop.finalproject.theMessageServer.characters;

import org.foop.finalproject.theMessageServer.Character;
import org.foop.finalproject.theMessageServer.Mission;
import org.foop.finalproject.theMessageServer.Skill;
import org.foop.finalproject.theMessageServer.enums.Gender;
import org.foop.finalproject.theMessageServer.missions.LaoJinMission;
import org.foop.finalproject.theMessageServer.skills.emptySkill;

public class LaoJin extends Character {
    public LaoJin(String name, Gender gender, Mission mission, Skill skill, boolean hidden) {
        super("老金", Gender.MAN, new LaoJinMission(), new emptySkill(), true);
    }
}
