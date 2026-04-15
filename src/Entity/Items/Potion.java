// Potion.java: Joseph
// heals 100 HP but cannot go over max HP
// print message is handled by ItemChoiceController not here

package Entity.Items;

import Entity.Combatant.Combatant;

import java.util.List;

public class Potion implements Item {

    // how much HP the potion restores
    private static final int healAmount = 100;
    private static final String name = "Potion";

    // return item name for inventory display and switch matching
    public String getName() {
        return name;
    }

    // heals player by fixed amount so that HP does not exceed max limit
    public void activateItem(Combatant user, Combatant enemy, List<Combatant> enemies) {
        user.setHp(Math.min(user.getHp() + healAmount, user.getMaxHp()));
    }
}
