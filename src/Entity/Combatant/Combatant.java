package Entity.Combatant;

import Entity.Effects.Effects;
import Entity.Items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Combatant {
    private int hp;
    private int maxHp;
    private int atk;
    private int def;
    private int spd;
    private String name;
    private int defDuration = 0;
    private int specialSkillCooldown = 0;
    public static final int max_items = 2;
    private List<Item> inventory = new ArrayList<>();
    private String specialSkillName;
    private List<Effects> statusEffects = new ArrayList<>();

    public Combatant(int hp, int atk, int def, int spd, String name, String specialSkillName) {
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        this.name = name;
        this.specialSkillName = specialSkillName;
    }

    public int getAtk() { return this.atk; }
    public int getDef() { return this.def; }
    public int getHp() { return this.hp; }

    public void setHp(int newHp) {
        // hp cant go below 0 or above max
        this.hp = Math.min(Math.max(newHp, 0), maxHp);
    }

    public void setDef(int newDef) { this.def = newDef; }
    public void setDefDuration(int newDuration) { this.defDuration = newDuration; }
    public void setName(String newName) { this.name = newName; }
    public int getSpeed() { return this.spd; }
    public int getDefDuration() { return this.defDuration; }
    public void setAtk(int newAtk) { this.atk = newAtk; }
    public void setSpecialSkillCooldown(int newDuration) { this.specialSkillCooldown = newDuration; }
    public int getSpecialSkillCooldown() { return this.specialSkillCooldown; }
    public int getMaxHp() { return this.maxHp; }
    public void initialiseInventory(List<Item> newInventory) { this.inventory = newInventory; }
    public void removeItemFromInventory(Item item) { this.inventory.remove(item); }
    public int getSpd() { return this.spd; }
    public String getName() { return this.name; }

    // checks if this combatant is the player using name match
    public boolean isUser() {
        return Objects.equals(name, "Wizard") || Objects.equals(name, "Warrior");
    }

    public List<Item> getInventory() { return this.inventory; }

    public boolean isSmokebombed() {
        for (Effects e : statusEffects) {
            if (Objects.equals(e.getEffectName(), "SmokebombEffect") && e.getDuration() > 0) {
                return true;
            }
        }
        return false;
    }

    public String getSpecialSkillName() { return this.specialSkillName; }

    public void decrementSkillCooldown() {
        if (specialSkillCooldown > 0) specialSkillCooldown--;
    }

    public void decrementEffectDuration() {
        statusEffects.forEach(Effects::decrementDuration);
        statusEffects.removeIf(Effects::hasExpired);
    }

    public List<Effects> getStatusEffects() { return this.statusEffects; }
    public void addStatusEffect(Effects effect) { this.statusEffects.add(effect); }

    public boolean isStunned() {
        for (Effects e : statusEffects) {
            if (Objects.equals(e.getEffectName(), "Stun") && e.getDuration() > 0) {
                return true;
            }
        }
        return false;
    }
}
