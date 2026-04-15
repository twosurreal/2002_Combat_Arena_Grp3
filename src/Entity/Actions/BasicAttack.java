// BasicAttack.java: Jarren
// removed isUser branch, smoke bomb early return
// damage formula is max(0, atk - def) so it never goes negative

package Entity.Actions;

import Entity.Combatant.Combatant;

import java.util.List;

public class BasicAttack implements Action {

    // basic attack always needs a target to hit
    public Boolean needsTarget() {
        return true;
    }

    // calculate damage and apply to target, skip if smoke bomb is active
    public void performAction(Combatant attacker, Combatant target, List<Combatant> enemies, Boolean usedPowerStone) {
        // smoke bomb active means all damage is blocked, no point continuing
        if (target.isSmokebombed()) {
            return;
        }

        // damage cant go below 0 if defense is higher than attack
        int dmg = Math.max(0, attacker.getAtk() - target.getDef());
        target.setHp(target.getHp() - dmg);

        System.out.println("\n" + attacker.getName() + " attacked " + target.getName() + " for " + dmg + " damage. " + target.getName() + " HP: " + target.getHp() + "/" + target.getMaxHp());
    }
}
