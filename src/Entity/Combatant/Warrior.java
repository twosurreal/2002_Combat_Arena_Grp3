// Warrior.java: Javier
// warrior player class with Shield Bash as special skill

package Entity.Combatant;

import Entity.Actions.Action;
import Entity.Actions.ShieldBash;

public class Warrior extends Player {

    // warrior stats
    private static final int hp = 260;
    private static final int atk = 40;
    private static final int def = 20;
    private static final int spd = 30;

    // name and skill name passed up to Combatant
    private static final String name = "Warrior";
    private static final String skillName = "Shield Bash";

    // pass all stats and skill name up to Player then Combatant
    public Warrior() {
        super(hp, atk, def, spd, name, skillName);
    }

    // return ShieldBash so BattleEngine can call it without knowing the class
    public Action getSpecialSkill() {
        return new ShieldBash();
    }

}
