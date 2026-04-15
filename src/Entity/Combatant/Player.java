// Player.java: Javier
// sits between Combatant and the actual player classes like Warrior and Wizard
// makes it easy to tell players apart from enemies

package Entity.Combatant;

// abstract so you cant make a plain Player, must use Warrior or Wizard
public abstract class Player extends Combatant {

    // pass all stats and skill name up to Combatant
    public Player(int hp, int atk, int def, int spd, String name, String specialSkillName) {
        super(hp, atk, def, spd, name, specialSkillName);
    }

}
