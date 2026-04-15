package Entity.Actions;

import Entity.Combatant.Combatant;
import Entity.Effects.ArcaneBlastBuff;

import java.util.List;

public class ArcaneBlast implements Action {
    private static final int skillCooldownDuration = 3;

    public Boolean needsTarget() {
        return false;
    }

    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        for (Combatant curEnemy : enemies) {

            int dmg = Math.max(0, user.getAtk() - curEnemy.getDef());
            curEnemy.setHp(curEnemy.getHp() - dmg);

            // give atk buff per kill
            if (curEnemy.getHp() <= 0) {
                ArcaneBlastBuff arcaneBuff = new ArcaneBlastBuff();
                user.addStatusEffect(arcaneBuff);
                user.setAtk(user.getAtk() + arcaneBuff.getAtkBuff());
            }
        }

        if (!usedPowerStone) {
            user.setSpecialSkillCooldown(skillCooldownDuration);
        }

        System.out.println("\nYou used Arcane Blast.");
    }
}
