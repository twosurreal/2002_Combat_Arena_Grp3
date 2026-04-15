package Entity.EnemyActions;

import Entity.Combatant.Combatant;

public interface EnemyActionStrategy {
    void performEnemyAction(Combatant user, Combatant target);
}
