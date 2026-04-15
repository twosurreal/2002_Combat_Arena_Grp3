// ActionChoiceController.java: Joshua
// main menu for player turns
// direct skill choice to right controllers
// if action is cancelled, it return false so player can pick again without losing their turn

package Control;

import Boundary.GamePlayScreen;
import Boundary.InputHelper;
import Entity.Actions.Action;
import Entity.Actions.BasicAttack;
import Entity.Actions.Defend;
import Entity.Combatant.Combatant;
import Entity.Items.Item;

import java.util.List;
import java.util.Scanner;

public class ActionChoiceController {

    private final ItemChoiceController itemChoiceController; //handle item selection and activaation
    private final TargetChoiceController targetChoiceController; //target selection from live enemy list
    private final SkillController skillController;  //scanner for everything
    private final Scanner scanner;  //check skill if available

    //create sub controllers and link the input scanner
    public ActionChoiceController() {
        itemChoiceController = new ItemChoiceController();
        targetChoiceController = new TargetChoiceController();
        skillController = new SkillController();
        scanner = InputHelper.getScanner();
    }

    // direct player's menu choice to the correct handle
    // return true to end turn, false to show the menu again if cancelled
    public boolean handleActionChoice(int actionChoice, Combatant user, List<Combatant> enemies, GamePlayScreen gameplayUI) {
        switch (actionChoice) {
            //basic attack
            case 1 -> {
                Combatant target = targetChoiceController.handleTargetChoice(scanner, enemies);
                if (target == null) return false; // cancelled
                new BasicAttack().performAction(user, target, enemies, false);
                return true;
            }
            //defend
            case 2 -> {
                new Defend().performAction(user, null, enemies, false);
                return true;
            }
            //use item
            case 3 -> {
                Item selectedItem = gameplayUI.displayItemChoice(user);
                if (selectedItem == null) return false; // cancelled
                return itemChoiceController.handleItemChoice(selectedItem, user, enemies);
            }
            //special skill
            case 4 -> {
                Action skill = skillController.getCombatantSkill(user);
                if (skill == null) return false; // on cooldown

                Combatant target = null;
                if (skill.needsTarget()) {
                    target = targetChoiceController.handleTargetChoice(scanner, enemies);
                    if (target == null) return false; // cancelled
                }
                //perform the skill
                skill.performAction(user, target, enemies, false);
                return true;
            }
            default -> { return false; }
        }
    }
}
