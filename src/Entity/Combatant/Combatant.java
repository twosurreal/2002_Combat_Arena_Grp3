// Combatant.java: Javier
// base class for everything that fights in the game
// players and enemies both extend this

package Entity.Combatant;

import Entity.Actions.Action;
import Entity.Effects.Effects;
import Entity.Effects.SmokebombEffect;
import Entity.Effects.Stun;
import Entity.Items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Combatant {

    private int hp;
    private int maxHp;
    private int atk;
    private int def;
    private int spd;

    private String name;
    private int specialSkillCooldown = 0;
    public static final int max_items = 2;
    private List<Item> inventory = new ArrayList<>();
    private String specialSkillName;
    private List<Effects> statusEffects = new ArrayList<>();

    private boolean justUsedSpecialSkill = false; // track if skill was used this turn
    private boolean eliminationAnnounced = false; // stop double defeat message
    private boolean powerstoneUsedThisRound = false; // track powerstone use per round

    public Combatant(int hp, int atk, int def, int spd, String name, String specialSkillName) {
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        this.name = name;
        this.specialSkillName = specialSkillName;
    }

    // each subclass returns its own skill object, polymorphic dispatch
    public abstract Action getSpecialSkill();

    public int getAtk() {
        return this.atk;
    }

    public int getDef() {
        return this.def;
    }

    public int getHp() {
        return this.hp;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public int getSpeed() {
        return this.spd;
    }

    public String getName() {
        return this.name;
    }

    public String getSpecialSkillName() {
        return this.specialSkillName;
    }

    public int getSpecialSkillCooldown() {
        return this.specialSkillCooldown;
    }

    public List<Item> getInventory() {
        return this.inventory;
    }

    public List<Effects> getStatusEffects() {
        return this.statusEffects;
    }

    public boolean hasJustUsedSpecialSkill() {
        return justUsedSpecialSkill;
    }

    // clamp HP between 0 and max so it never goes out of range
    public void setHp(int newHp) {
        this.hp = Math.min(Math.max(newHp, 0), maxHp);
    }

    // used by defend effect to raise and lower def
    public void setDef(int newDef) {
        this.def = newDef;
    }

    // used by arcane blast buff to raise atk on kill
    public void setAtk(int newAtk) {
        this.atk = newAtk;
    }

    // used by level setup to label enemies like goblin a, goblin b
    public void setName(String newName) {
        this.name = newName;
    }

    // set cooldown after using special skill
    public void setSpecialSkillCooldown(int newDuration) {
        this.specialSkillCooldown = newDuration;
    }

    // called by skill actions to mark that skill was used this turn
    public void setJustUsedSpecialSkill(boolean value) {
        this.justUsedSpecialSkill = value;
    }

    // check if defeat message has already been printed for this combatant
    public boolean isEliminationAnnounced() {
        return eliminationAnnounced;
    }

    // set to true after defeat message is printed to stop it printing again
    public void setEliminationAnnounced(boolean value) {
        this.eliminationAnnounced = value;
    }

    // check if powerstone was already used this round
    public boolean hasPowerstoneUsedThisRound() {
        return powerstoneUsedThisRound;
    }

    // flag powerstone as used so it cant be double triggered
    public void setPowerstoneUsedThisRound(boolean value) {
        this.powerstoneUsedThisRound = value;
    }

    // only decrement if there is still cooldown left
    public void decrementSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    // replace inventory list at game start with player chosen items
    public void initialiseInventory(List<Item> newInventory) {
        this.inventory = newInventory;
    }

    // remove item from inventory after it is used
    public void removeItemFromInventory(Item item) {
        this.inventory.remove(item);
    }

    // add a new status effect to this combatant
    public void addStatusEffect(Effects effect) {
        this.statusEffects.add(effect);
    }

    // check duration too because expired stuns were still blocking turns without it
    public boolean isStunned() {
        for (Effects e : statusEffects) {
            if (e instanceof Stun && e.getDuration() > 0) {
                return true;
            }
        }
        return false;
    }

    // return true if smoke bomb effect is active and still has duration left
    public boolean isSmokebombed() {
        for (Effects e : statusEffects) {
            if (e instanceof SmokebombEffect && e.getDuration() > 0) {
                return true;
            }
        }
        return false;
    }

}
