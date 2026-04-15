package Control;

import Boundary.GamePlayScreen;
import Entity.Combatant.Combatant;
import Entity.Effects.DefendEffect;
import Entity.Effects.Effects;
import Entity.EnemyActions.EnemyActionStrategy;
import Entity.Items.Item;
import Entity.Level;
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
    private boolean playerWon = false;
    private int roundNumber = 1;
    private int waveNumber = 1;

    public BattleEngine(
            Combatant user,
            List<Item> items,
            Level level,
            EnemyActionStrategy enemyActionStrategy,
            TurnOrderStrategy turnOrderStrategy,
            ActionChoiceController actionChoiceController,
            GamePlayScreen gameplayUI
    ) {
        this.user = user;
        user.initialiseInventory(items);
        this.activeEnemies = level.getActiveEnemies();
        this.backupEnemies = level.getBackupEnemies();
        this.enemyActionStrategy = enemyActionStrategy;
        this.turnOrderStrategy = turnOrderStrategy;
        this.actionChoiceController = actionChoiceController;
        this.gameplayUI = gameplayUI;
    }

    private void doUserAction() {
        while (true) {
            int actionChoice = gameplayUI.displayUserActions(user);
            boolean isSuccess = actionChoiceController.handleActionChoice(
                    actionChoice, user, activeEnemies, gameplayUI
            );
            if (isSuccess) break;
        }
    }

    private void doEnemyAction(Combatant enemy) {
        enemyActionStrategy.performEnemyAction(enemy, user);

        if (!user.isSmokebombed()) {
            System.out.println("\n" + enemy.getName() +
                    " attacked you. Your HP: " +
                    user.getHp() + " / " + user.getMaxHp());
        } else {
            System.out.println("\n" + enemy.getName() +
                    " attacked but Smoke Bomb blocked the damage!");
        }
    }

    public boolean playGameLoop() {
        while (!gameOver) {

            gameplayUI.displayCombatantStats(
                    roundNumber, waveNumber, activeEnemies, user
            );

            List<Combatant> allCombatants = new ArrayList<>(activeEnemies);
            allCombatants.add(user);

            List<Combatant> sortedCombatants =
                    turnOrderStrategy.decideTurnOrder(allCombatants);

            for (Combatant curCombatant : sortedCombatants) {
                if (curCombatant.getHp() <= 0 || gameOver) continue;

                boolean wasStunned = curCombatant.isStunned();

                if (wasStunned) {
                    System.out.println("\n" + curCombatant.getName() +
                            " is stunned! Turn skipped.");
                } else {
                    if (curCombatant.isUser()) {
                        doUserAction();
                    } else {
                        doEnemyAction(curCombatant);
                    }
                }

                activeEnemies.removeIf(enemy -> enemy.getHp() <= 0);

                // cooldown only ticks when the combatant actually took a turn
                if (curCombatant.skillJustUsed()) {
                    curCombatant.setSkillJustUsed(false);
                } else if (!wasStunned) {
                    curCombatant.decrementSkillCooldown();
                }

                checkGameStatus();

                if (gameOver) break;
            }

            if (!gameOver) {
                gameplayUI.displayRoundSummary(
                        roundNumber, waveNumber, activeEnemies, user
                );

                for (Combatant c : sortedCombatants) {
                    handleEffectDecrement(c);
                }

                handleBackupWave();
                roundNumber++;
            }
        }

        displayGameOverScreen();
        return playerWon;
    }

    private void handleEffectDecrement(Combatant combatant) {
        List<Effects> expiredEffects = new ArrayList<>();

        for (Effects effect : new ArrayList<>(combatant.getStatusEffects())) {
            effect.decrementDuration();

            if (effect.hasExpired()) {
                expiredEffects.add(effect);

                // handle cleanup per effect type on expiry
                switch (effect.getEffectName()) {
                    case "DefendEffect" -> {
                        DefendEffect defendEffect = (DefendEffect) effect;
                        combatant.setDef(
                                combatant.getDef() - defendEffect.getDefBoost()
                        );
                        System.out.println("\n" + combatant.getName() +
                                "'s Defend has worn off. DEF back to " +
                                combatant.getDef() + ".");
                    }
                    case "SmokeBombInvulnerability" ->
                            System.out.println("\n" + combatant.getName() +
                                    "'s Smoke Bomb protection has expired.");
                    case "Stun" ->
                            System.out.println("\n" + combatant.getName() +
                                    " is no longer stunned.");
                }
            }
        }

        combatant.getStatusEffects().removeAll(expiredEffects);
    }

    private void checkGameStatus() {
        if (user.getHp() <= 0) {
            gameOver = true;
            playerWon = false;
        } else if (activeEnemies.isEmpty() && backupEnemies.isEmpty()) {
            gameOver = true;
            playerWon = true;
        }
    }

    private void handleBackupWave() {
        if (!backupEnemies.isEmpty() && activeEnemies.isEmpty()) {
            System.out.println("\n=== WAVE " + waveNumber + " CLEARED ===");

            waveNumber++;

            activeEnemies.addAll(backupEnemies);
            backupEnemies.clear();

            System.out.println("=== WAVE " + waveNumber + " INCOMING ===");
            for (Combatant enemy : activeEnemies) {
                System.out.println(enemy.getName() +
                        " HP: " + enemy.getHp() +
                        " / " + enemy.getMaxHp());
            }
        }
    }

    private void displayGameOverScreen() {
        if (playerWon) {
            gameplayUI.displayVictory(user, roundNumber);
        } else {
            gameplayUI.displayDefeat(user, roundNumber, activeEnemies.size());
        }
    }
}
