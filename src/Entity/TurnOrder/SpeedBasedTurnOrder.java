// SpeedBasedTurnOrder.java: Jarren
// sorts combatants by speed, highest goes first
// sorts a copy so the original list order is not changed

package Entity.TurnOrder;

import Entity.Combatant.Combatant;

import java.util.ArrayList;
import java.util.List;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    public List<Combatant> decideTurnOrder(List<Combatant> combatants) {

        //create copy of combatant, sort them by speed in decreasing order
        List<Combatant> ordered = new ArrayList<>(combatants);
        ordered.sort((a, b) -> b.getSpeed() - a.getSpeed());
        return ordered;
    }
}
