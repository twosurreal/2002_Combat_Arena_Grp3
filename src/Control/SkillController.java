// SkillController.java: Joshua
// return null of the skill is not ready, allow player to pick different action

package Control;

import Entity.Actions.Action;
import Entity.Combatant.Combatant;

public class SkillController {

    //return the skill if ready or on cooldown
    //null tell controller to reset the menu so turn not wasted
    public Action getCombatantSkill(Combatant user) {
        if (user.getSpecialSkillCooldown() > 0) {
            return null;
        }
        return user.getSpecialSkill();
    }

    //return true if skill ready
    public boolean isSkillReady(Combatant user) {
        return user.getSpecialSkillCooldown() == 0;
    }
}
