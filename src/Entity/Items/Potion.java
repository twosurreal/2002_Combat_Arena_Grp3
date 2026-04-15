package Entity.Items;

import Entity.Combatant.Combatant;

import java.util.List;

public class Potion implements Item {
    private static final int heal_amount = 100;
    private static final String name = "Potion";

    public String getName() { return this.name; }

    public void activateItem(Combatant user, Combatant enemy, List<Combatant> enemies) {
        int newHp = Math.min(user.getHp() + heal_amount, user.getMaxHp());
        user.setHp(newHp);
        System.out.println("\nYou used Potion.");
    }
}
