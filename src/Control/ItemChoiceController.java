package Control;

import Entity.Actions.ArcaneBlast;
import Entity.Actions.ShieldBash;
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
                Potion potion = new Potion();
                potion.activateItem(user, null, enemies);
                System.out.println("\nYou used Potion. New HP: " + user.getHp() + " / " + user.getMaxHp());
            }
            case "Powerstone" -> {
                Powerstone powerstone = new Powerstone();
                switch (user.getName()) {
                    case "Wizard" -> {
                        powerstone.activateItem(user, null, enemies);
                    }
                    case "Warrior" -> {
                        Combatant selectedEnemy = tcc.handleTargetChoice(scanner, enemies);
                        powerstone.activateItem(user, selectedEnemy, enemies);
                    }
                    default -> {
                        return;
                    }
                }
            }
            case "Smokebomb" -> {
                Smokebomb smokebomb = new Smokebomb();
                smokebomb.activateItem(user, null, enemies);
            }
            default -> {
                return;
            }
        }

        user.removeItemFromInventory(selectedItem);
    }
}
