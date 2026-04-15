package Boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// all the screens shown before the battle starts
// purely display and input with no game logic in here at all
public class GameStartScreen {

    private static final int W = 56;
    private static final String LINE = "══════════════════════════════════════════════════════════";
    private static final String LTOP = "┌────────────────────────────────────────────────────────┐";
    private static final String LBOT = "└────────────────────────────────────────────────────────┘";

    private final Scanner scanner;

    public GameStartScreen() {
        // switched to inputhelper instead of creating a new scanner every time
        scanner = InputHelper.getScanner();
    }

    // helper function to force every line to exactly 56 characters
    // truncates long strings so they dont break the box borders
    private static void lrow(String content) {
        if (content.length() > W) {
            content = content.substring(0, W);
        }
        // pad with spaces to fill the box width
        while (content.length() < W) {
            content += " ";
        }
        System.out.println("│" + content + "│");
    }

    //print main game title banner and loading line
    public void displayLoadingScreen() {
        System.out.println(LINE);
        System.out.println("            === TURN-BASED COMBAT ARENA ===");
        System.out.println(LINE);
        System.out.println();
    }

    //display character selection menu with stat and special skill
    //include enemy base stats for player info
    public int displayCharacterScreen() {
        System.out.println(LTOP);
        lrow("  Choose Your Character");
        System.out.println("├" + "─".repeat(W) + "┤");
        lrow("  1. Warrior");
        lrow("     HP: 260  ATK: 40  DEF: 20  SPD: 30");
        lrow("     Special: Shield Bash");
        lrow("       Deals BasicAttack damage + stuns target 2 turns.");
        lrow("");
        lrow("  2. Wizard");
        lrow("     HP: 200  ATK: 50  DEF: 10  SPD: 20");
        lrow("     Special: Arcane Blast");
        lrow("       AoE damage; each kill adds +10 ATK");
        lrow("       until end of level.");
        System.out.println("├" + "─".repeat(W) + "┤");
        // showing enemy stats here so the player isnt going in blind
        lrow("  Enemies");
        lrow("    Goblin  HP:55  ATK:35  DEF:15  SPD:25");
        lrow("    Wolf    HP:40  ATK:45  DEF:5   SPD:35");
        System.out.println(LBOT);
        System.out.print("Enter choice (1-2): ");
        return InputHelper.getUserChoice(scanner, 1, 2, "!!! Invalid choice. Enter 1 or 2.");
    }

    //display itemshop and collect two user choices
    //return list of item id to potion, powerstone and smokebomb
    public List<Integer> displayItemScreen() {
        System.out.println(LTOP);
        lrow("  Choose Two Items  (duplicates allowed)");
        System.out.println("├" + "─".repeat(W) + "┤");
        lrow("  1. Potion      – Heal 100 HP (capped at max HP)");
        lrow("  2. Power Stone – Free extra use of special skill");
        lrow("                   Does not start or change cooldown.");
        lrow("  3. Smoke Bomb  – Enemy attacks deal 0 dmg this + next turn");
        System.out.println(LBOT);

        List<Integer> choices = new ArrayList<>();
        // displayitemscreen was originally returning a single int
        // changed it to a list once we realized they pick two items
        for (int i = 0; i < 2; i++) {
            System.out.print("Item " + (i + 1) + ": ");
            choices.add(InputHelper.getUserChoice(scanner, 1, 3,
                    "!!! Invalid choice. Enter 1, 2, or 3."));
        }
        return choices;
    }

    public int displayLevelScreen() {
        System.out.println(LTOP);
        lrow("  Select Difficulty");
        System.out.println("├" + "─".repeat(W) + "┤");
        lrow("  1. Easy");
        lrow("     Wave 1 : 3 Goblins");
        lrow("");
        lrow("  2. Medium");
        lrow("     Wave 1        : 1 Goblin + 1 Wolf");
        lrow("     Wave 2 (Backup): 2 Wolves");
        lrow("");
        lrow("  3. Hard");
        lrow("     Wave 1        : 2 Goblins");
        lrow("     Wave 2 (Backup): 1 Goblin + 2 Wolves");
        System.out.println(LBOT);
        System.out.print("Enter choice (1-3): ");
        return InputHelper.getUserChoice(scanner, 1, 3, "!!! Invalid choice. Enter 1, 2, or 3.");
    }

    public int displayReplayScreen() {
        System.out.println(LTOP);
        lrow("  Play Again?");
        System.out.println("├" + "─".repeat(W) + "┤");
        lrow("  1. Replay with same settings");
        lrow("  2. Start a new game");
        lrow("  3. Exit");
        System.out.println(LBOT);
        System.out.print("Enter choice: ");
        return InputHelper.getUserChoice(scanner, 1, 3, "!!! Invalid choice. Enter 1, 2, or 3.");
    }
}