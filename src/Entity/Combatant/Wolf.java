package Entity.Combatant;

public class Wolf extends Combatant {
    private static final int hp = 40;
    private static final int atk = 45;
    private static final int def = 5;
    private static final int spd = 35;
    private static final String name = "Wolf";

    public Wolf() {
        super(hp, atk, def, spd, name, "");
    }
}
