package Entity.Items;

import Entity.Combatant.Combatant;

import java.util.List;

public interface Item {
    String getName();
    void activateItem(Combatant user, Combatant enemy, List<Combatant> enemies);
}
