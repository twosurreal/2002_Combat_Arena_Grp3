package Entity.Combatant;

public class Goblin extends Combatant {
    private static final int hp = 55;
    private static final int atk = 35;
    private static final int def = 15;
    private static final int spd = 25;
    private static final String name = "Goblin";

    public Goblin() {
        super(hp, atk, def, spd, name, "");
    }
}
