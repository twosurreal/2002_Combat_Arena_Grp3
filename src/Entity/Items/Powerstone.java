package Entity.Items;

import Entity.Actions.ArcaneBlast;
import Entity.Actions.ShieldBash;
import Entity.Combatant.Combatant;

import java.util.List;

public class Powerstone implements Item {
    private static final String name = "PowerStone";

    public String getName() {
        return this.name;
    }

    public void activateItem(Combatant user, Combatant enemy, List<Combatant> enemies) {
        switch (user.getName()) {
            case "Wizard" -> {
                ArcaneBlast arcaneBlast = new ArcaneBlast();
                arcaneBlast.performAction(user, null, enemies, true);
            }
            case "Warrior" -> {
                ShieldBash shieldBash = new ShieldBash();
                shieldBash.performAction(user, enemy, enemies, true);
            }
            default -> {
                return;
            }
        }
        System.out.println("\nYou used Power Stone.");
    }
}
