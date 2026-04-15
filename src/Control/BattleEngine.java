package Control;

import Boundary.GamePlayScreen;
import Entity.Combatant.Combatant;
import Entity.EnemyActions.EnemyActionStrategy;
import Entity.EnemyActions.EnemyBasicAttack;
import Entity.Items.Item;
import Entity.Level;
import Entity.TurnOrder.SpeedBasedTurnOrder;
import Entity.TurnOrder.TurnOrderStrategy;

import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private final Combatant user;
    private final List<Combatant> activeEnemies;
    private final List<Combatant> backupEnemies;
    private final ActionChoiceController actionChoiceController;
    private final GamePlayScreen gameplayUI;
    private final EnemyActionStrategy enemyActionStrategy;
    private final TurnOrderStrategy turnOrderStrategy;
    private boolean gameOver = false;
    private int roundNumber = 1;

    public BattleEngine(Combatant user, List<Item> items, Level level) {
        this.user = user;
        user.initialiseInventory(items);
        this.activeEnemies = level.getActiveEnemies();
        this.backupEnemies = level.getBackupEnemies();
        actionChoiceController = new ActionChoiceController();
        enemyActionStrategy = new EnemyBasicAttack();
        turnOrderStrategy = new SpeedBasedTurnOrder();
        gameplayUI = new GamePlayScreen();
    }

    public void playGameLoop() {
        while (!gameOver) {

            gameplayUI.displayCombatantStats(roundNumber, activeEnemies, user);

            List<Combatant> allCombatants = new ArrayList<>(activeEnemies);
            allCombatants.add(user);
            List<Combatant> sortedCombatants = turnOrderStrategy.decideTurnOrder(allCombatants);

            // decrement effects and cooldowns before anyone acts
            for (Combatant c : sortedCombatants) {
                c.decrementEffectDuration();
                c.decrementSkillCooldown();
            }

            for (Combatant curCombatant : sortedCombatants) {

                if (curCombatant.getHp() <= 0 || gameOver) continue;

                if (curCombatant.isStunned()) {
                    System.out.println("\n" + curCombatant.getName() + " is stunned! Turn skipped.");
                } else {
                    if (curCombatant.isUser()) doUserAction();
                    else doEnemyAction(curCombatant);
                }

                checkGameStatus();

                if (gameOver) break;
            }

            activeEnemies.removeIf(enemy -> enemy.getHp() <= 0);

            handleBackupWave();

            roundNumber++;
        }
    }

    private void doUserAction() {
        while (true) {
            int actionChoice = gameplayUI.displayUserActions();
            boolean isSuccess = actionChoiceController.handleActionChoice(actionChoice, user, activeEnemies, gameplayUI);
            activeEnemies.removeIf(c -> c.getHp() <= 0);

            if (isSuccess) break;
        }
    }

    private void doEnemyAction(Combatant enemy) {
        enemyActionStrategy.performEnemyAction(enemy, user);
        System.out.println("\n" + enemy.getName() + " attacked you. Your HP: " + user.getHp() + " / " + user.getMaxHp());
    }

    private void checkGameStatus() {
        if (user.getHp() <= 0) {
            gameOver = true;
            System.out.println("\n=== DEFEAT ===");
        } else if (activeEnemies.isEmpty() && backupEnemies.isEmpty()) {
            gameOver = true;
            System.out.println("\n=== VICTORY ===");
        }
    }

    private void handleBackupWave() {
        if (!backupEnemies.isEmpty() && activeEnemies.isEmpty()) {
            activeEnemies.addAll(backupEnemies);
            backupEnemies.clear();
            System.out.println("\n=== BACK UP ENEMIES APPEARED ===");
        }
    }
}
