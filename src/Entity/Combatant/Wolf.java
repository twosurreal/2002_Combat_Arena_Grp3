// Wolf.java: Javier
// wolf enemy with fixed stats from the spec
// faster than goblin but lower defense

package Entity.Combatant;

public class Wolf extends Enemy {

    // wolf stats, high spd and atk but low def
    private static final int hp = 40;
    private static final int atk = 45;
    private static final int def = 5;
    private static final int spd = 35;

    // name used by setName in level setup to label wolf a, b
    private static final String name = "Wolf";

    // pass all stats up to Enemy then Combatant
    public Wolf() {
        super(hp, atk, def, spd, name);
    }

}
