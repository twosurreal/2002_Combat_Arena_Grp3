package Boundary;

import Entity.Combatant.Combatant;
import Entity.Items.Item;
import Entity.Level;

import java.util.*;

public class GamePlayScreen {
    private final Scanner scanner;

    public GamePlayScreen() {
        scanner = new Scanner(System.in);
    }

    public void displayCombatantStats(int roundNumber, List<Combatant> activeEnemies, Combatant user) {
        System.out.println("\n===== ROUND " + roundNumber + " ======");
        System.out.println("\nYour HP: " + user.getHp() + " / " + user.getMaxHp());
        for (Combatant curEnemy : activeEnemies) {
            System.out.println(curEnemy.getName() + " HP: " + curEnemy.getHp() + " / " + curEnemy.getMaxHp());
        }
    }

    public int displayUserActions() {
        int actionChoice;
        while (true) {
            System.out.println("\nSelect action: ");
            System.out.println("1. Basic Attack");
            System.out.println("2. Defend");
            System.out.println("3. Use Item");
            System.out.println("4. Use Special Skill");

            actionChoice = scanner.nextInt();

            if (actionChoice < 1 || actionChoice > 4) continue;

            break;
        }

        return actionChoice;
    }

    public Item displayItemChoice(Combatant user) {
        Item selectedItem;
        while (true) {
            System.out.println("\nSelect item: ");
            int i = 1;
            for (Item item : user.getInventory()) {
                System.out.println(i + ". " + item.getName());
                i++;
            }

            int itemChoice = scanner.nextInt();

            if (itemChoice < 1 || itemChoice > user.getInventory().size()) {
                System.out.println("\n!!! Invalid choice.");
                continue;
            }

            selectedItem = user.getInventory().get(itemChoice - 1);

            break;
        }

        return selectedItem;
    }
}
