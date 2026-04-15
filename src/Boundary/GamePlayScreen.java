package Boundary;

import Entity.Combatant.Combatant;
import Entity.Effects.Effects;
import Entity.Items.Item;

import java.util.List;
import java.util.Scanner;

public class GamePlayScreen {
    private final Scanner scanner;

    public GamePlayScreen() {
        scanner = new Scanner(System.in);
    }

    public void displayCombatantStats(int roundNumber, int waveNumber, List<Combatant> activeEnemies, Combatant user) {
        System.out.println("\n=== ROUND " + roundNumber + " | WAVE " + waveNumber + " ===");
        System.out.println("Your HP: " + user.getHp() + "/" + user.getMaxHp());
        System.out.println("ATK: " + user.getAtk() + "  DEF: " + user.getDef() + "  SPD: " + user.getSpeed());

        String effectsDisplay = "";
        for (Effects e : user.getStatusEffects()) {
            if (!e.getEffectName().equals("ArcaneBlastBuff")) {
                effectsDisplay += "[" + e.getEffectName() + " " + e.getDuration() + "t] ";
            }
        }
        if (!effectsDisplay.isEmpty()) {
            System.out.println("Effects: " + effectsDisplay.trim());
        }

        if (user.getSpecialSkillCooldown() > 0) {
            System.out.println("Skill: " + user.getSpecialSkillName() + " (cooldown: " + user.getSpecialSkillCooldown() + ")");
        } else {
            System.out.println("Skill: " + user.getSpecialSkillName() + " [READY]");
        }

        System.out.println("Items: " + user.getInventory().size() + " remaining");

        System.out.println("\nENEMIES");
        for (Combatant enemy : activeEnemies) {
            String stunTag = enemy.isStunned() ? " [STUNNED]" : "";
            System.out.println("  " + enemy.getName() + "  HP: " + enemy.getHp() + "/" + enemy.getMaxHp() + stunTag);
        }
    }

    public int displayUserActions(Combatant user) {
        String skillLine = user.getSpecialSkillCooldown() > 0
                ? user.getSpecialSkillName() + " (CD:" + user.getSpecialSkillCooldown() + ")"
                : user.getSpecialSkillName() + " [READY]";

        String itemLine = user.getInventory().isEmpty()
                ? "Use Item (none remaining)"
                : "Use Item (" + user.getInventory().size() + " remaining)";

        System.out.println("\n--- PLAYER TURN ---");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. " + itemLine);
        System.out.println("4. " + skillLine);
        System.out.print("Choose action: ");

        while (true) {
            int choice = InputHelper.getUserChoice(scanner, 1, 4, "!!! Invalid choice. Enter 1-4.");
            if (choice == 3 && user.getInventory().isEmpty()) {
                System.out.println("!!! No items remaining.");
                System.out.print("Choose action: ");
                continue;
            }
            if (choice == 4 && user.getSpecialSkillCooldown() > 0) {
                System.out.println("!!! Skill still on cooldown (" + user.getSpecialSkillCooldown() + " turn(s)).");
                System.out.print("Choose action: ");
                continue;
            }
            return choice;
        }
    }

    public Item displayItemChoice(Combatant user) {
        while (true) {
            System.out.println("\nSelect item:");
            int i = 1;
            for (Item item : user.getInventory()) {
                System.out.println("  " + i + ". " + item.getName());
                i++;
            }
            System.out.print("Enter choice: ");
            int itemChoice = InputHelper.getUserChoice(scanner, 1, user.getInventory().size(), "!!! Invalid choice.");
            return user.getInventory().get(itemChoice - 1);
        }
    }

    public void displayRoundSummary(int roundNumber, int waveNumber, List<Combatant> activeEnemies, Combatant user) {
        System.out.println("\n--- End of Round " + roundNumber + " | Wave " + waveNumber + " ---");
        System.out.println("Your HP: " + user.getHp() + "/" + user.getMaxHp());
        for (Combatant enemy : activeEnemies) {
            String stunTag = enemy.isStunned() ? " [STUNNED]" : "";
            System.out.println("  " + enemy.getName() + " HP: " + enemy.getHp() + "/" + enemy.getMaxHp() + stunTag);
        }
        System.out.println("Items: " + user.getInventory().size() + " remaining");
        if (user.getSpecialSkillCooldown() > 0) {
            System.out.println("Skill cooldown: " + user.getSpecialSkillCooldown() + " turn(s)");
        } else {
            System.out.println("Skill: READY");
        }
    }

    public void displayVictory(Combatant user, int rounds) {
        int potionCount = countItem(user, "Potion");
        int powerCount = countItem(user, "PowerStone");
        int smokeCount = countItem(user, "SmokeBomb");

        System.out.println("\n=== VICTORY ===");
        System.out.println("HP: " + user.getHp() + "/" + user.getMaxHp());
        System.out.println("Rounds: " + rounds);
        System.out.println("Remaining Potions: " + potionCount);
        System.out.println("Remaining Power Stones: " + powerCount);
        System.out.println("Remaining Smoke Bombs: " + smokeCount);
    }

    public void displayDefeat(Combatant user, int rounds, int enemiesLeft) {
        int potionCount = countItem(user, "Potion");
        int powerCount = countItem(user, "PowerStone");
        int smokeCount = countItem(user, "SmokeBomb");

        System.out.println("\n=== DEFEAT ===");
        System.out.println("Rounds: " + rounds);
        System.out.println("Enemies Left: " + enemiesLeft);
        System.out.println("Remaining Potions: " + potionCount);
        System.out.println("Remaining Power Stones: " + powerCount);
        System.out.println("Remaining Smoke Bombs: " + smokeCount);
    }

    private int countItem(Combatant user, String itemName) {
        int count = 0;
        for (Item item : user.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) count++;
        }
        return count;
    }
}
