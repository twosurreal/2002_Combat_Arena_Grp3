package Entity.Actions;

import Entity.Combatant.Combatant;

import java.util.List;

public class BasicAttack implements Action {

    public Boolean needsTarget() {
        return true;
    }

    public void performAction(Combatant attacker, Combatant target, List<Combatant> enemies, Boolean usedPowerStone) {
        if (target.isSmokebombed()) {
            System.out.println("\n!!! " + attacker.getName() + " missed.");
            return;
        }

        int dmg = Math.max(0, attacker.getAtk() - target.getDef());
        target.setHp(target.getHp() - dmg);

        if (attacker.isUser()) {
            System.out.println("\nYou attacked " + target.getName() + ". " + target.getName() + " HP: " + target.getHp() + " / " + target.getMaxHp());
        }
    }
}
