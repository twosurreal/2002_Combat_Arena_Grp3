package Entity.Actions;

import Entity.Combatant.Combatant;

import java.util.List;

public interface Action {
    Boolean needsTarget();
    void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone);
}
