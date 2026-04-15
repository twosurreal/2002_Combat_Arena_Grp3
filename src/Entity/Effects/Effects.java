package Entity.Effects;

public interface Effects {
    void decrementDuration();
    int getDuration();
    boolean hasExpired();
    String getEffectName();
}
