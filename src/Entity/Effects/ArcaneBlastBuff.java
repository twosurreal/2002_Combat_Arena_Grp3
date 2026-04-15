package Entity.Effects;

public class ArcaneBlastBuff implements Effects {
    private int remainingDuration = 0;
    private int atkBuffPerKill = 10;

    public boolean hasExpired() { return false; }
    public void decrementDuration() {}
    public int getDuration() { return remainingDuration; }
    public String getEffectName() { return "ArcaneBlastBuff"; }
    public int getAtkBuff() { return this.atkBuffPerKill; }
}
