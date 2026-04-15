package Entity.Combatant;

public class Wizard extends Combatant {
    private static final int hp = 200;
    private static final int atk = 50;
    private static final int def = 10;
    private static final int spd = 20;
    private static final String name = "Wizard";
    private static final String skillName = "Arcane Blast";

    public Wizard() {
        super(hp, atk, def, spd, name, skillName);
    }
}
