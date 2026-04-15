package Control;

import Entity.Combatant.Combatant;

import java.util.List;
import java.util.Scanner;

public class TargetChoiceController {

    public Combatant handleTargetChoice(Scanner scanner, List<Combatant> enemies) {
        while (true) {
            System.out.println("\nSelect enemy: ");
            int i = 1;
            for (Combatant curEnemy : enemies) {
                System.out.println(i + ". " + curEnemy.getName());
                i++;
            }

            int enemyChoice;
            try {
                enemyChoice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("\n!!! Invalid input. Enter a number.");
                continue;
            }

            if (enemyChoice > enemies.size() || enemyChoice < 1) {
                System.out.println("\n!!! Invalid choice.");
                continue;
            }

            return enemies.get(enemyChoice - 1);
        }
    }
}
