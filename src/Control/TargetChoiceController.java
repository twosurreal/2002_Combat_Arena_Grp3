// TargetChoiceController.java: Joshua
// 0 to cancel, returns null on cancel

package Control;

import Boundary.InputHelper;
import Entity.Combatant.Combatant;

import java.util.List;
import java.util.Scanner;

public class TargetChoiceController {

    //show enemy to pick a target, 0 return null to cancel
    //if 1+ return enemy
    public Combatant handleTargetChoice(Scanner scanner, List<Combatant> enemies) {
        System.out.println("\nSelect target (0 to cancel):");
        int i = 1;
        for (Combatant enemy : enemies) {
            System.out.println("  " + i + ". " + enemy.getName() + "  HP: " + enemy.getHp() + "/" + enemy.getMaxHp());
            i++;
        }
        System.out.print("Enter choice: ");
        int choice = InputHelper.getUserChoice(scanner, 0, enemies.size(), "!!! Invalid choice.");
        if (choice == 0) return null;
        return enemies.get(choice - 1);
    }
}
