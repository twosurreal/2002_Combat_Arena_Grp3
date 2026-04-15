package Entity.TurnOrder;

import Entity.Combatant.Combatant;

import java.util.*;

public interface TurnOrderStrategy {
    List<Combatant> decideTurnOrder(List<Combatant> combatants);
}
