package Entity.Effects;

public class SmokebombEffect implements Effects {
    private int remainingDuration;
    private final int initialDuration = 2;

    public SmokebombEffect() {
        remainingDuration = initialDuration;
    }

    public boolean hasExpired() { return remainingDuration <= 0; }
    public void decrementDuration() { remainingDuration--; }
    public int getDuration() { return remainingDuration; }
    public String getEffectName() { return "SmokebombEffect"; }
}
