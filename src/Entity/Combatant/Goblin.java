// Goblin.java: Javier
// goblin enemy with fixed stats from the spec

package Entity.Combatant;

// goblin is the weaker enemy, slower and lower atk than wolf
public class Goblin extends Enemy {

    // stats are static final so they are shared and never change
    private static final int hp = 55;
    private static final int atk = 35;
    private static final int def = 15;
    private static final int spd = 25;

    // name used by setName in level setup to label goblin a, b, c
    private static final String name = "Goblin";

    // pass all stats up to Enemy then Combatant
    public Goblin() {
        super(hp, atk, def, spd, name);
    }

}
