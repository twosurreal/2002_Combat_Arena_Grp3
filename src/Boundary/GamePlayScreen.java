//Gameplayscreen: Brennen
//manage ui for battle
//handle HUD format, action menu input collection

package Boundary;

import Entity.Combatant.Combatant;
import Entity.Effects.ArcaneBlastBuff;
import Entity.Effects.Effects;
import Entity.Items.Item;

import java.util.List;
import java.util.Scanner;

// everything the player sees during a battle
// no combat logic here just display and input collection
public class GamePlayScreen {

    //ui for boxes
    private static final int W = 56;
    private static final String TOP  = "╔════════════════════════════════════════════════════════╗";
    private static final String DIV  = "╠════════════════════════════════════════════════════════╣";
    private static final String BOT  = "╚════════════════════════════════════════════════════════╝";
    private static final String LTOP = "┌────────────────────────────────────────────────────────┐";
    private static final String LBOT = "└────────────────────────────────────────────────────────┘";

    private final Scanner scanner; //initialise scanner

    //constructor: link screen to system scanner
    public GamePlayScreen() {
        scanner = InputHelper.getScanner();
    }

    // ── box helpers ───────────────────────────────────────────────────────

    //format and print single line with border
    private static void row(String content) {
        if (content.length() > W) {
            content = content.substring(0, W);
        }
        // pad with spaces to fill the box width
        while (content.length() < W) {
            content += " ";
        }
        System.out.println("║" + content + "║");
    }

    //format and print a single line with light-boarder vertical pipe
    //handle clipping long string and padd short ones
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
    // ── display helpers ───────────────────────────────────────────────────

    // translates internal class names to something readable in the ui
    public static String friendlyItemName(String raw) {
        return switch (raw) {
            case "SmokeBomb"  -> "Smoke Bomb";
            case "PowerStone" -> "Power Stone";
            default           -> raw;
        };
    }
    //convert effect class name into label for ui
    private static String friendlyEffectName(String raw) {
        return switch (raw) {
            case "DefendEffect"             -> "Defend";
            case "SmokeBombInvulnerability" -> "Smoke Bomb";
            default                         -> raw;
        };
    }

    // groups duplicate items so we get potion x2 instead of two separate lines
    private static String buildItemLine(Combatant user) {
        if (user.getInventory().isEmpty()) return "Items: None";

        int potionCount = 0;
        int powerStoneCount = 0;
        int smokeBombCount = 0;

        //iterate through inventory and increment counter for each item type
        for (Item item : user.getInventory()) {
            switch (item.getName()) {
                case "Potion" -> potionCount++;
                case "PowerStone" -> powerStoneCount++;
                case "SmokeBomb" -> smokeBombCount++;
            }
        }

        //initialise item string
        String result = "Items: ";
        boolean first = true;

        //append item count to string with divider
        if (potionCount > 0) {
            result += "Potion x" + potionCount;
            first = false;
        }
        if (powerStoneCount > 0) {
            if (!first) result += " | ";
            result += "Power Stone x" + powerStoneCount;
            first = false;
        }
        if (smokeBombCount > 0) {
            if (!first) result += " | ";
            result += "Smoke Bomb x" + smokeBombCount;
        }

        return result;
    }

    // ── combat hud ────────────────────────────────────────────────────────

    //display main hud
    //has player stats, active effect and enemy status
    public void displayCombatantStats(int roundNumber, int waveNumber, List<Combatant> activeEnemies, Combatant user) {
        System.out.println("\n" + TOP);
        row("  ROUND " + roundNumber + "  |  WAVE " + waveNumber);
        System.out.println(DIV);
        row("  HP:  " + user.getHp() + "/" + user.getMaxHp());
        row("  ATK: " + user.getAtk() + "   DEF: " + user.getDef() + "   SPD: " + user.getSpeed());

        // arcaneblastbuff is hidden here because it already shows as a higher atk value above
        // effects row only prints if there are actually effects to show
        String fx = "";
        for (Effects e : user.getStatusEffects()) {
            if (!(e instanceof ArcaneBlastBuff)) {
                fx += "[" + friendlyEffectName(e.getEffectName()) + " " + e.getDuration() + "t] ";
            }
        }
        if (!fx.isEmpty()) row("  Effects: " + fx.trim()); //display active status effect and duration

        if (user.getSpecialSkillCooldown() > 0) { //print current skill status
            row("  Skill: " + user.getSpecialSkillName() + " (cooldown: " + user.getSpecialSkillCooldown() + ")");
        } else {
            row("  Skill: " + user.getSpecialSkillName() + " [READY]");
        }
        row("  " + buildItemLine(user));

        //print enemy section of ui, list each enemy hp and stun status
        System.out.println(DIV);
        row("  ENEMIES");
        for (Combatant enemy : activeEnemies) {
            String stun = enemy.isStunned() ? " [STUNNED]" : "";
            row("  " + enemy.getName()
                    + "   HP: " + enemy.getHp() + "/" + enemy.getMaxHp() + stun);
        }
        System.out.println(BOT);
    }

    // ── action menu ───────────────────────────────────────────────────────

    //display action menu and check input
    //prompts the player again to select an empty inventory or skill on cooldown
    public int displayUserActions(Combatant user) {
        String skillLine = user.getSpecialSkillCooldown() > 0
                ? user.getSpecialSkillName() + " (CD:" + user.getSpecialSkillCooldown() + ")"
                : user.getSpecialSkillName() + " [READY]";

        System.out.println("\n" + TOP);
        row("              PLAYER TURN");
        System.out.println(DIV);
        row("  1. Basic Attack");
        row("  2. Defend");
        row("  3. Use Item");
        row("  4. " + skillLine);
        System.out.println(BOT);
        System.out.print("Choose action: ");

        while (true) {
            int choice = InputHelper.getUserChoice(scanner, 1, 4, "!!! Invalid choice. Enter 1-4.");

            // conscious bce trade-off here
            // checking if the inventory is empty right here in the boundary layer
            // stops us from passing a useless choice all the way up to the control layer
            if (choice == 3 && user.getInventory().isEmpty()) {
                System.out.println("!!! No items remaining.");
                System.out.print("Choose action: ");
                continue;
            }
            if (choice == 4 && user.getSpecialSkillCooldown() > 0) {
                System.out.println("!!! Skill on cooldown ("
                        + user.getSpecialSkillCooldown() + " turn(s)).");
                System.out.print("Choose action: ");
                continue;
            }
            return choice;
        }
    }

    //list all inventory for selection
    //return chosen item or null if aciton is cancelled
    public Item displayItemChoice(Combatant user) {
        System.out.println("\nSelect item (0 to cancel):");
        int i = 1;
        for (Item item : user.getInventory()) {
            System.out.println("  " + i + ". " + friendlyItemName(item.getName()));
            i++;
        }
        System.out.print("Enter choice: ");
        int itemChoice = InputHelper.getUserChoice(scanner, 0, user.getInventory().size(),
                "!!! Invalid choice.");
        if (itemChoice == 0) return null;
        return user.getInventory().get(itemChoice - 1);
    }

    // ── end-of-round summary ──────────────────────────────────────────────

    //display recap of hp and cooldown at the end of every round
    public void displayRoundSummary(int roundNumber, int waveNumber,
                                    List<Combatant> activeEnemies, Combatant user) {
        System.out.println("\n" + LTOP);
        lrow("  End of Round " + roundNumber + " | Wave " + waveNumber);
        System.out.println("├" + "─".repeat(W) + "┤");
        lrow("  Your HP: " + user.getHp() + "/" + user.getMaxHp());
        for (Combatant enemy : activeEnemies) {
            String stun = enemy.isStunned() ? " [STUNNED]" : "";
            lrow("  " + enemy.getName()
                    + " HP: " + enemy.getHp() + "/" + enemy.getMaxHp() + stun);
        }
        System.out.println("├" + "─".repeat(W) + "┤");
        lrow("  " + buildItemLine(user));
        if (user.getSpecialSkillCooldown() > 0) {
            lrow("  Skill cooldown: " + user.getSpecialSkillCooldown() + " turn(s)");
        } else {
            lrow("  Skill: READY");
        }
        System.out.println(LBOT);
    }

    // ── end screens ───────────────────────────────────────────────────────

    //display victory screen with final health, total round and item remaining
    public void displayVictory(Combatant user, int rounds) {
        System.out.println("\n" + TOP);
        row("              !!! VICTORY !!!");
        System.out.println(DIV);
        row("  Congratulations, you have defeated all your enemies.");
        System.out.println(DIV);
        row("  Remaining HP: " + user.getHp() + "/" + user.getMaxHp());
        row("  Total Rounds: " + rounds);
        System.out.println(DIV);
        row("  " + buildItemLine(user));
        System.out.println(BOT);
    }

    //display defeat scren showing surviving enemy and total round survive
    public void displayDefeat(Combatant user, int rounds, int enemiesLeft) {
        System.out.println("\n" + TOP);
        row("               !!! DEFEAT !!!");
        System.out.println(DIV);
        row("  Defeated. Don't give up, try again!");
        System.out.println(DIV);
        row("  Enemies Remaining: " + enemiesLeft);
        row("  Total Rounds Survived: " + rounds);
        System.out.println(DIV);
        row("  " + buildItemLine(user));
        System.out.println(BOT);
    }
}