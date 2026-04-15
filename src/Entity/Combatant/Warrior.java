package Entity.Combatant;

public class Warrior extends Combatant {
    private static final int hp = 260;
    private static final int atk = 40;
    private static final int def = 20;
    private static final int spd = 30;
    private static final String name = "Warrior";
    private static final String skillName = "Shield Bash";

    public Warrior() {
        super(hp, atk, def, spd, name, skillName);
    }

    public boolean isUser() { return true; }
}
