// DefendEffect.java: Joseph
// resetDuration to prevent stacking
// DEF gets reverted in onExpire when the effect runs out

package Entity.Effects;

import Entity.Combatant.Combatant;

public class DefendEffect implements Effects {

    // track how many turns are left on this effect
    private int remainingDuration;
    private final int initialDuration = 2;
    private final int defBoost = 10; // +10 DEF for 2 turns

    // start at full duration when first applied
    public DefendEffect() {
        remainingDuration = initialDuration;
    }

    // if player defends again while effect is active, just reset the timer
    // this stops the player from stacking multiple +10 DEF
    public void resetDuration() {
        remainingDuration = initialDuration;
    }

    // return def boost so Defend action can apply and remove it correctly
    public int getDefBoost() {
        return defBoost;
    }

    // count down each turn
    public void decrementDuration() {
        remainingDuration--;
    }

    // return remaining turns left
    public int getDuration() {
        return remainingDuration;
    }

    // effect is done when duration hits zero
    public boolean hasExpired() {
        return remainingDuration <= 0;
    }

    // return name used for display in hud
    public String getEffectName() {
        return "DefendEffect";
    }

    // remove the DEF boost when effect wears off
    public void onExpire(Combatant target) {
        target.setDef(target.getDef() - defBoost);
        System.out.println("\n" + target.getName()
                + "'s Defend has worn off. DEF back to " + target.getDef() + ".");
    }
}
