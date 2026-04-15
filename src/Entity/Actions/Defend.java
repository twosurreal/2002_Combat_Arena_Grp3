package Entity.Actions;

import Entity.Combatant.Combatant;
import Entity.Effects.DefendEffect;

import java.util.List;

public class Defend implements Action {
    private static final String name = "Defend";
    private static final boolean needTarget = false;

    public Boolean needsTarget() {
        return false;
    }

    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        DefendEffect defendEffect = new DefendEffect();
        user.setDef(user.getDef() + defendEffect.getDefBoost());
        user.addStatusEffect(defendEffect);
    }
}
