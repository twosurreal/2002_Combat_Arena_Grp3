// Powerstone.java: Joseph
// triggers special skill for free, cooldown stays unchanged
// CD +1 trick here cancels the end of round decrement so net change is zero

package Entity.Items;

import Entity.Actions.Action;
import Entity.Combatant.Combatant;

import java.util.List;

public class Powerstone implements Item {

    // item name used for inventory display and switch matching
    private static final String name = "PowerStone";

    // return item name for inventory display and switch matching
    public String getName() {
        return this.name;
    }

    // bump cooldown by 1 first so end of round decrement cancels it out, then fire the skill
    public void activateItem(Combatant user, Combatant enemy, List<Combatant> enemies) {
        // add 1 to cooldown now so when BattleEngine decrements at end of round it cancels out
        user.setSpecialSkillCooldown(user.getSpecialSkillCooldown() + 1);
        System.out.println("\nYou used Power Stone - triggering " + user.getSpecialSkillName() + "!");
        Action skill = user.getSpecialSkill();
        skill.performAction(user, enemy, enemies, true);
    }
}
