package Entity.Effects;

public class DefendEffect implements Effects {
    private int remainingDuration;
    private final int initialDuration = 2;
    private final int defBoost = 10;

    public DefendEffect() {
        remainingDuration = initialDuration;
    }

    public void decrementDuration() {
        remainingDuration--;
    }

    public int getDuration() {
        return remainingDuration;
    }

    public boolean hasExpired() {
        return remainingDuration <= 0;
    }

    public String getEffectName() {
        return "DefendEffect";
    }

    public int getDefBoost() {
        return defBoost;
    }
}
