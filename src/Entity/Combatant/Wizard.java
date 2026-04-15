// Wizard.java: Javier
// wizard player class with Arcane Blast as special skill

package Entity.Combatant;

import Entity.Actions.Action;
import Entity.Actions.ArcaneBlast;

public class Wizard extends Player {

    // wizard stats
    private static final int hp = 200;
    private static final int atk = 50;
    private static final int def = 10;
    private static final int spd = 20;

    // name and skill name passed up to Combatant
    private static final String name = "Wizard";
    private static final String skillName = "Arcane Blast";

    // pass all stats and skill name up to Player then Combatant
    public Wizard() {
        super(hp, atk, def, spd, name, skillName);
    }

    // return ArcaneBlast so BattleEngine can call it without knowing the class
    public Action getSpecialSkill() {
        return new ArcaneBlast();
    }

}
