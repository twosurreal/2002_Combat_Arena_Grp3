// EnemyBasicAttack.java: Javier
// implements EnemyActionStrategy
// reuses BasicAttack so enemy damage formula stays the same as player

package Entity.EnemyActions;

import Entity.Actions.Action;
import Entity.Actions.BasicAttack;
import Entity.Combatant.Combatant;

public class EnemyBasicAttack implements EnemyActionStrategy {
    // enemy just does a basic attack on the player every turn
    public void performEnemyAction(Combatant enemy, Combatant target) {
        Action basicAttack = new BasicAttack();
        basicAttack.performAction(enemy, target, null, false);
    }
}
