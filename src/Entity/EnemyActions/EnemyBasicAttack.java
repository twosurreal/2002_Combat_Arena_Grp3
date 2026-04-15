package Entity.EnemyActions;

import Entity.Actions.Action;
import Entity.Actions.BasicAttack;
import Entity.Combatant.Combatant;

public class EnemyBasicAttack implements EnemyActionStrategy {

    public void performEnemyAction(Combatant user, Combatant target) {
        Action basicAttack = new BasicAttack();
        basicAttack.performAction(user, target, null, false);
    }
}
