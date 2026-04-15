package Entity.Actions;

import Entity.Combatant.Combatant;
import Entity.Effects.ArcaneBlastBuff;

import java.util.List;

public class ArcaneBlast implements Action {
    private static final String name = "Arcane Blast";
    private static final boolean needTarget = false;
    private static final int skillCooldownDuration = 3;

    public Boolean needsTarget() {
        return false;
    }

    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        int kills = 0;

        for (Combatant curEnemy : enemies) {
            // only hit enemies that are still alive
            if (curEnemy.getHp() <= 0) continue;

            int dmg = Math.max(0, user.getAtk() - curEnemy.getDef());
            curEnemy.setHp(curEnemy.getHp() - dmg);

            if (curEnemy.getHp() <= 0) {
                kills++;
            }
        }

        // apply atk buff per kill after the full loop
        for (int i = 0; i < kills; i++) {
            ArcaneBlastBuff arcaneBuff = new ArcaneBlastBuff();
            user.addStatusEffect(arcaneBuff);
            user.setAtk(user.getAtk() + arcaneBuff.getAtkBuff());
        }

        user.setSkillJustUsed(true);

        if (!usedPowerStone) {
            user.setSpecialSkillCooldown(3);
        }

        System.out.println("\nYou used Arcane Blast.");
    }
}
