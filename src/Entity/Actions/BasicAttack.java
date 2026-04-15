package Entity.Actions;

import Entity.Combatant.Combatant;

import java.util.List;

public class BasicAttack implements Action {
    private static final String name = "Basic Attack";
    private static final boolean needTarget = true;

    public Boolean needsTarget() {
        return true;
    }

    public void performAction(Combatant attacker, Combatant target, List<Combatant> enemies, Boolean usedPowerStone) {
        if (target.isSmokebombed()) {
            // smoke bomb blocks incoming damage, no hp change
            return;
        }

        int dmg = Math.max(0, attacker.getAtk() - target.getDef());
        target.setHp(target.getHp() - dmg);

        if (attacker.isUser()) {
            System.out.println("\nYou attacked " + target.getName() + " for " + dmg + " damage. "
                    + target.getName() + " HP: " + target.getHp() + " / " + target.getMaxHp());
        }
    }
}
