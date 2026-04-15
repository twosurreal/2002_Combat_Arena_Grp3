package Control;

import Entity.Actions.Action;
import Entity.Actions.ArcaneBlast;
import Entity.Actions.ShieldBash;
import Entity.Combatant.Combatant;

import java.util.List;
import java.util.Scanner;

public class SkillController {

    public Action getCombatantSkill(Combatant user) {
        return switch (user.getName()) {
            case "Warrior" -> new ShieldBash();
            case "Wizard" -> new ArcaneBlast();
            default -> null;
        };
    }
}
