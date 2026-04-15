// ItemChoiceController.java: Joshua
// Run item effect and remove from inventory
//powerstone check for target first, cancelling target stops it from being used

package Control;

import Boundary.InputHelper;
import Entity.Actions.Action;
import Entity.Combatant.Combatant;
import Entity.Items.Item;
import Entity.Items.Powerstone;

import java.util.List;
import java.util.Scanner;

public class ItemChoiceController {

    private final TargetChoiceController tcc;
    private final Scanner scanner;

    //constructor: initialise target selection controller and get the scanner
    public ItemChoiceController() {
        tcc = new TargetChoiceController();
        scanner = InputHelper.getScanner();
    }

    //use item and handle the logic
    //check for target, run effect, print a message, and remove item from inventory
    public boolean handleItemChoice(Item selectedItem, Combatant user, List<Combatant> enemies) {
        Combatant target = null;

        // if using powerstone, check if skill need a target
        //ask for the target first so that skill know who to hit
        if (selectedItem instanceof Powerstone) {
            Action skill = user.getSpecialSkill();

            //prompt for a target if skill require one, cancel item if no target selected
            if (skill.needsTarget()) {
                target = tcc.handleTargetChoice(scanner, enemies);
                if (target == null) return false; // player cancelled
            }
        }

        //activate the item
        //target is null if the item does not need one or target the player
        selectedItem.activateItem(user, target, enemies);

        // print confirm messages for potions and smokebomb
        //powerstone handle own messaging
        switch (selectedItem.getName()) {
            case "Potion" ->
                    System.out.println("\nYou used Potion. HP: "
                            + user.getHp() + "/" + user.getMaxHp());
            case "SmokeBomb" ->
                    System.out.println("\nYou used Smoke Bomb. Enemy attacks deal 0 damage this turn and next.");
        }
        //remove item consume from inventory
        user.removeItemFromInventory(selectedItem);
        return true;
    }
}
