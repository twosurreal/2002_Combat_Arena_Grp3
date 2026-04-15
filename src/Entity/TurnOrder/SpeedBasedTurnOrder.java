package Entity.TurnOrder;

import Entity.Combatant.Combatant;

import java.util.*;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    public List<Combatant> decideTurnOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);
        ordered.sort((a, b) -> b.getSpeed() - a.getSpeed());
        return ordered;
    }
}
