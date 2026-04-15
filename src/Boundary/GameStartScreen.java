package Boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameStartScreen {
    private final Scanner scanner;

    private static final String LINE = "═══════════════════════════════════════════════════════";

    public GameStartScreen() {
        scanner = new Scanner(System.in);
    }

    public void displayLoadingScreen() {
        System.out.println(LINE);
        System.out.println("         === TURN-BASED COMBAT ARENA ===   ");
        System.out.println(LINE);
        System.out.println();
    }

    public int displayCharacterScreen() {
        System.out.println();
        System.out.println("┌─ Choose Your Character ─────────────────────────────────┐");
        System.out.println("│  1. Warrior                                             │");
        System.out.println("│     HP: 260  ATK: 40  DEF: 20  SPD: 30                  │");
        System.out.println("│     Special: Shield Bash - Deals damage + stuns target  │");
        System.out.println("│                                                         │");
        System.out.println("│  2. Wizard                                              │");
        System.out.println("│     HP: 200  ATK: 50  DEF: 10  SPD: 20                  │");
        System.out.println("│     Special: Arcane Blast - AoE damage; kills boost ATK │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        System.out.println();
        System.out.println("┌─ Enemies Stats ──────────────────────────────────────────┐");
        System.out.println("│  Goblin  HP:55  ATK:35  DEF:15  SPD:25                   │");
        System.out.println("│  Wolf    HP:40  ATK:45  DEF:5   SPD:35                   │");
        System.out.println("└──────────────────────────────────────────────────────────┘");

        System.out.print("Enter choice (1-2): ");
        return InputHelper.getUserChoice(scanner, 1, 2, "!!! Invalid choice. Enter 1 or 2.");
    }

    public List<Integer> displayItemScreen() {
        System.out.println();
        System.out.println("┌─ Choose Two Items ─────────────────────────────────────────┐");
        System.out.println("│  1. Potion      - Heal 100 HP (capped at max HP)          │");
        System.out.println("│  2. Power Stone - Free extra special skill use            │");
        System.out.println("│                   (does not affect cooldown)              │");
        System.out.println("│  3. Smoke Bomb  - Enemy attacks deal 0 dmg this+next turn │");
        System.out.println("└───────────────────────────────────────────────────────────┘");
        System.out.println("Select 2 items (duplicates allowed):");

        List<Integer> itemChoices = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            System.out.print("Item " + (i + 1) + ": ");
            itemChoices.add(InputHelper.getUserChoice(scanner, 1, 3, "!!! Invalid choice. Enter 1, 2, or 3."));
        }
        return itemChoices;
    }

    public int displayLevelScreen() {
        System.out.println();
        System.out.println("┌─ Select Difficulty ──────────────────────────────────────┐");
        System.out.println("│  1. Easy   - Wave 1: 3 Goblins                           │");
        System.out.println("│             No backup spawn                              │");
        System.out.println("│                                                          │");
        System.out.println("│  2. Medium - Wave 1: 1 Goblin + 1 Wolf                   │");
        System.out.println("│             Backup spawn: 2 Wolves                       │");
        System.out.println("│                                                          │");
        System.out.println("│  3. Hard   - Wave 1: 2 Goblins                           │");
        System.out.println("│             Backup spawn: 1 Goblin + 2 Wolves            │");
        System.out.println("└──────────────────────────────────────────────────────────┘");

        System.out.print("Enter choice (1-3): ");
        return InputHelper.getUserChoice(scanner, 1, 3, "!!! Invalid choice. Enter 1, 2, or 3.");
    }

    public int displayReplayScreen() {
        System.out.println("\n╔════════════════════════════╗");
        System.out.println("║        GAME OVER           ║");
        System.out.println("╠════════════════════════════╣");
        System.out.println("║  1. Replay same settings   ║");
        System.out.println("║  2. New game               ║");
        System.out.println("║  3. Exit                   ║");
        System.out.println("╚════════════════════════════╝");
        System.out.print("Enter choice: ");
        return InputHelper.getUserChoice(scanner, 1, 3, "!!! Invalid choice. Enter 1, 2, or 3.");
    }
}
