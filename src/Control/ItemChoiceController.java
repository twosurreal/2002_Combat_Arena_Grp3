package Control;

import Entity.Combatant.Combatant;
import Entity.Items.Item;
import Entity.Items.Potion;
import Entity.Items.Powerstone;
import Entity.Items.Smokebomb;

import java.util.List;
import java.util.Scanner;

public class ItemChoiceController {
    private final TargetChoiceController tcc;
    private final Scanner scanner;

    public ItemChoiceController() {
        tcc = new TargetChoiceController();
        scanner = new Scanner(System.in);
    }

    public void handleItemChoice(Item selectedItem, Combatant user, List<Combatant> enemies) {
        switch (selectedItem.getName()) {
            case "Potion" -> {
                selectedItem.activateItem(user, null, enemies);
                System.out.println("\nYou used Potion. New HP: " + user.getHp() + " / " + user.getMaxHp());
            }
            case "PowerStone" -> {
                switch (user.getName()) {
                    case "Wizard" -> {
                        selectedItem.activateItem(user, null, enemies);
                    }
                    case "Warrior" -> {
                        Combatant selectedEnemy = tcc.handleTargetChoice(scanner, enemies);
                        selectedItem.activateItem(user, selectedEnemy, enemies);
                    }
                    default -> {
                        return;
                    }
                }
            }
            case "SmokeBomb" -> {
                selectedItem.activateItem(user, null, enemies);
                System.out.println("\nYou used Smoke Bomb. Enemy attacks deal 0 damage this turn and next.");
            }
            default -> {
                return;
            }
        }

        user.removeItemFromInventory(selectedItem);
    }
}
