// ArcaneBlastBuff.java: Joseph
// permanent ATK buff from Arcane Blast kills
// getDuration returns -1 to signal it never expires

package Entity.Effects;

import Entity.Combatant.Combatant;

public class ArcaneBlastBuff implements Effects {

    // each kill gives wizard +10 ATK
    private final int atkBuffPerKill = 10;

    // return the atk boost amount so ArcaneBlast can apply it
    public int getAtkBuff() {
        return atkBuffPerKill;
    }

    // permanent effect, duration never goes down
    public void decrementDuration() {
    }

    // -1 means permanent, used by isSmokebombed and isStunned checks to not expire this
    public int getDuration() {
        return -1;
    }

    // never expires so always return false
    public boolean hasExpired() {
        return false;
    }

    // return name used for display and effect lookup
    public String getEffectName() {
        return "ArcaneBlastBuff";
    }

    // ATK was already added directly so nothing to undo here
    public void onExpire(Combatant target) {
    }
}
