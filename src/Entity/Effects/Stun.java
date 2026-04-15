package Entity.Effects;

public class Stun implements Effects {
    private int remainingDuration;
    private int initialDuration = 2;

    public Stun() {
        remainingDuration = initialDuration;
    }

    public boolean hasExpired() { return remainingDuration <= 0; }
    public void decrementDuration() { remainingDuration--; }
    public int getDuration() { return remainingDuration; }
    public String getEffectName() { return "Stun"; }
}
