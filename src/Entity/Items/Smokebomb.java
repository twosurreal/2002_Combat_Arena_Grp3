package Entity.Items;

import Entity.Combatant.Combatant;
import Entity.Effects.SmokebombEffect;

import java.util.List;

public class Smokebomb implements Item {
    private static final String name = "Smokebomb";

    public String getName() { return this.name; }

    public void activateItem(Combatant user, Combatant enemy, List<Combatant> enemies) {
        user.addStatusEffect(new SmokebombEffect());
        System.out.println("\nYou used Smoke Bomb.");
    }
}
