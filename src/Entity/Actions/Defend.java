// Defend.java: Jarren
// prevent stacking with resetDuration
// instanceof replaces string compare to check if effect is already active

package Entity.Actions;

import Entity.Combatant.Combatant;
import Entity.Effects.DefendEffect;
import Entity.Effects.Effects;

import java.util.List;

public class Defend implements Action {

    // defend targets self, no enemy selection needed
    public Boolean needsTarget() {
        return false;
    }

    // check if defend already active, refresh or apply fresh effect
    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        // if defend already active just reset the timer, dont add another +10
        for (Effects e : user.getStatusEffects()) {
            if (e instanceof DefendEffect) {
                ((DefendEffect) e).resetDuration();
                System.out.println("\nDefend refreshed. DEF +" + ((DefendEffect) e).getDefBoost()
                        + " reset to 2 turns. (DEF: " + user.getDef() + ")");
                return;
            }
        }

        // no active defend effect found so apply a new one
        DefendEffect defendEffect = new DefendEffect();
        user.setDef(user.getDef() + defendEffect.getDefBoost());
        user.addStatusEffect(defendEffect);
        System.out.println("\nYou take a defensive stance. DEF +"
                + defendEffect.getDefBoost() + " for 2 turns. (DEF: " + user.getDef() + ")");
    }
}
