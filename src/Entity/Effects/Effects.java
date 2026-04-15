// Effects.java: Joseph
// onExpire added, cleanup now handled inside each effect class
// each effect knows how to remove itself when it runs out

package Entity.Effects;

import Entity.Combatant.Combatant;

public interface Effects {

    // reduce remaining duration by 1 each turn
    void decrementDuration();

    // return current duration
    // return -1 for permanent effects like ArcaneBlastBuff
    int getDuration();

    // return true if the effect should be removed
    boolean hasExpired();

    String getEffectName();

    // called when effect runs out so the effect can clean up after itself
    void onExpire(Combatant target);
}
