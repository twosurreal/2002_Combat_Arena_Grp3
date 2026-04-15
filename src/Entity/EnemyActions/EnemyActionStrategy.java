// EnemyActionStrategy.java: Javier
// defines how an enemy acts on its turn
// new enemy behaviour types can be added without changing BattleEngine

package Entity.EnemyActions;

import Entity.Combatant.Combatant;

// any class that wants to define enemy behaviour must implement this
public interface EnemyActionStrategy {
    void performEnemyAction(Combatant enemy, Combatant target);
}
