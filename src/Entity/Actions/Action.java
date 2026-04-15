// Action.java: Jarren
// common interface for all combat actions
// every action must say if it needs a target and how to perform itself

package Entity.Actions;

import Entity.Combatant.Combatant;

import java.util.List;

public interface Action {

    // return true if action needs player to pick a target before it runs
    Boolean needsTarget();

    // run the action
    // usedPowerStone true means triggered by item, skip setting cooldown
    void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone);
}
