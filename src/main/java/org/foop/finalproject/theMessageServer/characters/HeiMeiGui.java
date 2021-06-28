package org.foop.finalproject.theMessageServer.characters;

import org.foop.finalproject.theMessageServer.Character;
import org.foop.finalproject.theMessageServer.Mission;
import org.foop.finalproject.theMessageServer.Skill;
import org.foop.finalproject.theMessageServer.enums.Gender;
import org.foop.finalproject.theMessageServer.missions.HeiMeiGuiMission;
import org.foop.finalproject.theMessageServer.skills.emptySkill;

public class HeiMeiGui extends Character {
    public HeiMeiGui() {
        super("HeiMeiGui", Gender.WOMAN, new HeiMeiGuiMission(), new emptySkill(), true);
    }
}
