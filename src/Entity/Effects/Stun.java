// Stun.java: Joseph
// 2 turn duration, stops target from acting
// dead target guard in onExpire so message doesnt print for already eliminated enemies

package Entity.Effects;

import Entity.Combatant.Combatant;

public class Stun implements Effects {

    // track how many turns the stun lasts
    private int remainingDuration;
    private final int initialDuration = 2;

    // start at full duration when stun is applied
    public Stun() {
        remainingDuration = initialDuration;
    }

    // count down each turn
    public void decrementDuration() {
        remainingDuration--;
    }

    // return remaining turns left
    public int getDuration() {
        return remainingDuration;
    }

    // stun is done when duration hits zero
    public boolean hasExpired() {
        return remainingDuration <= 0;
    }

    // return name used for display in hud
    public String getEffectName() {
        return "Stun";
    }

    // only print the message if the target is still alive when stun expires
    public void onExpire(Combatant target) {
        if (target.getHp() > 0) {
            System.out.println("\n" + target.getName() + " is no longer stunned.");
        }
    }
}
