// Smokebomb.java: Joseph
// applies SmokebombEffect on use
// actual damage blocking logic is inside BasicAttack and BattleEngine

package Entity.Items;

import Entity.Combatant.Combatant;
import Entity.Effects.SmokebombEffect;

import java.util.List;

// smokebomb just applies the effect, the effect class handles duration and expiry
public class Smokebomb implements Item {

    // item name used for inventory display and switch matching
    private static final String name = "SmokeBomb";

    // return item name for inventory display and switch matching
    public String getName() {
        return name;
    }

    // just add the effect, smokebombEffect handles the duration and expiry
    public void activateItem(Combatant user, Combatant enemy, List<Combatant> enemies) {
        user.addStatusEffect(new SmokebombEffect());
    }
}
