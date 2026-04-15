// Enemy.java: Javier
// sits between Combatant and the actual enemy classes like Goblin and Wolf
// makes it easy to tell players apart from enemies

package Entity.Combatant;

import Entity.Actions.Action;

// abstract so you cant make a plain Enemy, must use Goblin or Wolf
public abstract class Enemy extends Combatant {

    // enemies dont have a special skill name so just pass empty string
    public Enemy(int hp, int atk, int def, int spd, String name) {
        super(hp, atk, def, spd, name, "");
    }

    // enemies have no special skill so just return null
    public Action getSpecialSkill() {
        return null;
    }

}
