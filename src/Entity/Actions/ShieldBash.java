// ShieldBash.java: Jarren
// warrior special skill
// stun only applied if target survives the hit

package Entity.Actions;

import Entity.Combatant.Combatant;
import Entity.Effects.Stun;

import java.util.List;

public class ShieldBash implements Action {

    // shield bash needs a target to hit
    public Boolean needsTarget() {
        return true;
    }

    // deal damage then stun target if still alive
    public void performAction(Combatant user, Combatant enemy,
                              List<Combatant> enemies, Boolean usedPowerStone) {
        int dmg = Math.max(0, user.getAtk() - enemy.getDef());
        enemy.setHp(enemy.getHp() - dmg);

        // flag so BattleEngine knows skill was used this turn
        user.setJustUsedSpecialSkill(true);
        if (!usedPowerStone) {
            user.setSpecialSkillCooldown(3);
        }

        // print damage result before checking stun
        System.out.println("\nYou used Shield Bash on " + enemy.getName() + " for " + dmg + " damage.  " + enemy.getName() + " HP: " + enemy.getHp() + "/" + enemy.getMaxHp());

        // only stun if enemy still alive after the hit
        if (enemy.getHp() > 0) {
            enemy.addStatusEffect(new Stun());
            System.out.println(enemy.getName() + " is stunned!");
        }
    }
}
