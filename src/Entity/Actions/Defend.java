package Entity.Actions;

import Entity.Combatant.Combatant;

import java.util.List;

public class Defend implements Action {
    private static final int defDuration = 2;
    private static final int defBoost = 10;

    public Boolean needsTarget() {
        return false;
    }

    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        user.setDef(user.getDef() + defBoost);
        user.setDefDuration(defDuration);
        System.out.println("\nYou used Defense Boost. New DEF: " + user.getDef());
    }
}
