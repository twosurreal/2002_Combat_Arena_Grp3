package Entity.Actions;

import Entity.Combatant.Combatant;
import Entity.Effects.Stun;

import java.util.List;

public class ShieldBash implements Action {
    private static final int skillCooldown = 3;

    public Boolean needsTarget() {
        return true;
    }

    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        int dmg = Math.max(0, user.getAtk() - enemy.getDef());
        enemy.setHp(enemy.getHp() - dmg);

        enemy.addStatusEffect(new Stun());

        if (!usedPowerStone) {
            user.setSpecialSkillCooldown(skillCooldown);
        }

        System.out.println("\nYou used Shield Bash.");
    }
}
