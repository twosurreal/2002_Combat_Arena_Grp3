package Entity.Actions;

import Entity.Combatant.Combatant;
import Entity.Effects.Stun;

import java.util.List;

public class ShieldBash implements Action {
    private static final String name = "Shield Bash";
    private static final boolean needTarget = true;
    private static final int skillCooldown = 3;
    private static final int stunDuration = 2;

    public Boolean needsTarget() {
        return true;
    }

    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        int dmg = Math.max(0, user.getAtk() - enemy.getDef());
        enemy.setHp(enemy.getHp() - dmg);

        //enemy.setStunDuration(stunDuration);
        enemy.addStatusEffect(new Stun());

        user.setSkillJustUsed(true);

        if (!usedPowerStone) {
            user.setSpecialSkillCooldown(3);
        }

        System.out.println("\nYou used Shield Bash.");
    }
}
