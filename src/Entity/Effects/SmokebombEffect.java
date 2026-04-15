// SmokebombEffect.java: Joseph
// 2 turn invulnerability against enemy attacks
// prints expiry message in onExpire

package Entity.Effects;

import Entity.Combatant.Combatant;

public class SmokebombEffect implements Effects {

    // track how many turns of protection are left
    private int remainingDuration;
    private final int initialDuration = 2;

    // start at full duration when first used
    public SmokebombEffect() {
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

    // effect is done when duration hits zero
    public boolean hasExpired() {
        return remainingDuration <= 0;
    }

    // return name used for display in hud
    public String getEffectName() {
        return "SmokeBombInvulnerability";
    }

    // let player know the smoke bomb protection is gone
    public void onExpire(Combatant target) {
        System.out.println("\n" + target.getName() + "'s Smoke Bomb protection has expired.");
    }
}
