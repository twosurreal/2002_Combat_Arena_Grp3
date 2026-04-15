// ArcaneBlast.java: Jarren
// wizard special skill
// hits all enemies, each kill gives wizard +10 ATK for the rest of the level

package Entity.Actions;

import Entity.Combatant.Combatant;
import Entity.Effects.ArcaneBlastBuff;

import java.util.List;

public class ArcaneBlast implements Action {

    // no target needed, hits everyone
    public Boolean needsTarget() {
        return false;
    }

    // loop through all enemies, deal damage, count how many we killed
    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        System.out.println("\nYou unleash Arcane Blast on all enemies!");

        int kills = 0; // track how many enemies died this hit

        // go through each enemy one by one
        for (Combatant curEnemy : enemies) {
            if (curEnemy.getHp() <= 0) continue; // already dead, skip

            // damage formula same as basic attack
            int dmg = Math.max(0, user.getAtk() - curEnemy.getDef());
            curEnemy.setHp(curEnemy.getHp() - dmg);

            boolean eliminated = curEnemy.getHp() <= 0;
            System.out.println("  " + curEnemy.getName() + " takes " + dmg + " damage.  HP: " + curEnemy.getHp() + "/" + curEnemy.getMaxHp() + (eliminated ? "  [ELIMINATED]" : ""));

            // count the kill and flag it so BattleEngine doesnt print second defeat message
            if (eliminated) {
                kills++;
                // mark so BattleEngine doesnt print a second defeat message for this enemy
                curEnemy.setEliminationAnnounced(true);
            }
        }

        // apply ATK buff after the full loop
        // applying mid loop made later enemies take more damage than they should
        for (int i = 0; i < kills; i++) {
            ArcaneBlastBuff buff = new ArcaneBlastBuff();
            user.addStatusEffect(buff);
            user.setAtk(user.getAtk() + buff.getAtkBuff());
        }

        // print atk gain message only if at least one enemy was killed
        if (kills > 0) {
            System.out.println("Killed " + kills + " enem" + (kills == 1 ? "y" : "ies")
                    + "! ATK +" + (kills * 10) + " (now " + user.getAtk() + ") for rest of level.");
        }

        // flag skill used and set cooldown if not triggered by powerstone
        user.setJustUsedSpecialSkill(true);
        if (!usedPowerStone) {
            user.setSpecialSkillCooldown(3);
        }
    }
}
