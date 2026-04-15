package Control;

import Boundary.GamePlayScreen;
import Entity.Actions.*;
import Entity.Combatant.Combatant;
import Entity.Items.Item;

import java.util.List;
import java.util.Scanner;

public class ActionChoiceController {
    private final ItemChoiceController itemChoiceController;
    private final TargetChoiceController targetChoiceController;
    private final SkillController skillController;
    private final Scanner scanner;

    public ActionChoiceController() {
        itemChoiceController = new ItemChoiceController();
        targetChoiceController = new TargetChoiceController();
        skillController = new SkillController();
        scanner = new Scanner(System.in);
    }

    private Action convertActionChoice(int actionChoice, Combatant user, List<Combatant> enemies, GamePlayScreen gameplayUI) {
        switch(actionChoice) {
            case 1 -> {
                return new BasicAttack();
            }
            case 2 -> {
                return new Defend();
            }
            case 3 -> {
                Item selectedItem = gameplayUI.displayItemChoice(user);
                return new ItemAction(selectedItem, itemChoiceController);
            }
            case 4 -> {
                if (user.getSpecialSkillCooldown() > 0) {
                    System.out.println("\n!!! Skill in cooldown.");
                    return null;
                }

                return skillController.getCombatantSkill(user);
            }
            default -> {
                return null;
            }
        }
    }

    public boolean handleActionChoice(int actionChoice, Combatant user, List<Combatant> enemies, GamePlayScreen gameplayUI) {
        Action selectedAction = convertActionChoice(actionChoice, user, enemies, gameplayUI);

        if (selectedAction == null) return false;

        Combatant selectedEnemy = null;
        if (selectedAction.needsTarget()) {
            selectedEnemy = targetChoiceController.handleTargetChoice(scanner, enemies);
        }

        selectedAction.performAction(user, selectedEnemy, enemies, false);

        // return true indicate action executed successfully
        return true;
    }
}
