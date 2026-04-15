// TurnOrderStrategy.java: Jarren
// interface for deciding who goes first each round
// new turn order types can be added without changing BattleEngine

package Entity.TurnOrder;

import Entity.Combatant.Combatant;

import java.util.List;

//define method to determine sequence of combat turns
public interface TurnOrderStrategy {
    List<Combatant> decideTurnOrder(List<Combatant> combatants);
}
