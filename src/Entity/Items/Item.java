// Item.java: Joseph
// common interface for all usable items in the game
// new item types can be added without changing other classes

package Entity.Items;

import Entity.Combatant.Combatant;

import java.util.List;

public interface Item {

    String getName();

    // enemy param only needed by powerstone since skill may need a target
    void activateItem(Combatant user, Combatant enemy, List<Combatant> enemies);
}
