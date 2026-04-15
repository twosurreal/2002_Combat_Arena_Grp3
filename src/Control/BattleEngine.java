// BattleEngine.java: Joshua
//manage main battle loop such as turn order, waves, wind/loss
//decrement cooldown at the end of the round and update the ui/gameplay screen

package Control;

import Boundary.GamePlayScreen;
import Entity.Combatant.Combatant;
import Entity.Effects.Effects;
import Entity.EnemyActions.EnemyActionStrategy;
import Entity.Items.Item;
import Entity.Level;
import Entity.TurnOrder.TurnOrderStrategy;

import java.util.ArrayList;
import java.util.List;

public class BattleEngine {

    private final Combatant user; //user character
    private final List<Combatant> activeEnemies; //wave 1 enemy
    private final List<Combatant> backupEnemies; //wave 2 enemy
    private final ActionChoiceController actionChoiceController; //direct player to menu
    private final GamePlayScreen gameplayUI; //boundary layer
    private final EnemyActionStrategy enemyActionStrategy; // turns enemy strategy
    private final TurnOrderStrategy turnOrderStrategy; //turn orders
    private boolean gameOver = false; //game not over
    private boolean playerWon = false; //player has not lost a game yet
    private int roundNumber = 1; // show round num
    private int waveNumber = 1; //wave num

    //set up player start inventory in combatant object
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

    //promp player for action
    //loop until action confirm
    //if cancel, menu opens again without wasting turn
    private void doUserAction() {
        while (true) {
            int actionChoice = gameplayUI.displayUserActions(user);
            boolean isSuccess = actionChoiceController.handleActionChoice(
                    actionChoice, user, activeEnemies, gameplayUI
            );
            if (isSuccess) break;
        }
    }

    //execute enemy turn
    //if smokebomb active, attack block
    private void doEnemyAction(Combatant enemy) {
        if (!user.isSmokebombed()) {
            enemyActionStrategy.performEnemyAction(enemy, user);
        } else {
            System.out.println("\n" + enemy.getName()
                    + " attacked but Smoke Bomb blocked the damage!");
        }
    }

    //handle cleanup after any turn
    //remove defeated enemy
    //flag ensure death message only print one time per enemy
    private void resolveAfterAction() {
        for (Combatant enemy : activeEnemies) {
            if (enemy.getHp() <= 0 && !enemy.isEliminationAnnounced()) {
                System.out.println("\n" + enemy.getName() + " has been defeated!");
            }
        }
        activeEnemies.removeIf(enemy -> enemy.getHp() <= 0); //remove all dead enemy from list
        checkGameStatus(); //check if game should end
    }

    //main battle loop
    //display hud and check speed turn order
    //execute turn
    //cleanup dead enemies and if game is over
    //print summary, check status effects and spawn backup wave
    //update state of cooldowns and round counter
    //return true if victory, false for defeat
    public boolean playGameLoop() {
        while (!gameOver) {

            //hud/ui
            gameplayUI.displayCombatantStats(roundNumber, waveNumber, activeEnemies, user);

            //put all combatants into one list for order
            List<Combatant> allCombatants = new ArrayList<>(activeEnemies);
            allCombatants.add(user);
            List<Combatant> sortedCombatants = turnOrderStrategy.decideTurnOrder(allCombatants);

            //print turn order
            System.out.print("\nTurn order: ");
            for (int i = 0; i < sortedCombatants.size(); i++) {
                System.out.print(sortedCombatants.get(i).getName()
                        + " (SPD:" + sortedCombatants.get(i).getSpeed() + ")");
                if (i < sortedCombatants.size() - 1) System.out.print(" -> ");
            }
            System.out.println();

            //execute each combatant turn
            for (Combatant curCombatant : sortedCombatants) {
                if (curCombatant.getHp() <= 0 || gameOver) continue; //skip dead combatants and stop loop if game end in round

                boolean wasStunned = curCombatant.isStunned();
                //label enemy turn
                if (curCombatant != user) {
                    System.out.println("\n--- " + curCombatant.getName() + "'s turn ---");
                }

                if (wasStunned) { //stun skip action, print message
                    System.out.println(curCombatant.getName() + " is stunned! Turn skipped.");
                } else {
                    if (curCombatant == user) { //not stunned
                        doUserAction(); //player prompt action menu
                    } else {
                        doEnemyAction(curCombatant);
                    }
                }

                resolveAfterAction(); //check deaths or game over

                if (gameOver) break;
            }

            // always print end of the round summary even on final round
            gameplayUI.displayRoundSummary(roundNumber, waveNumber, activeEnemies, user);

            // handle effect tick and wave progress
            if (!gameOver) {
                for (Combatant c : sortedCombatants) {
                    if (c.getHp() <= 0) continue;
                    handleEffectDecrement(c);
                }
                handleBackupWave();
            }

            //decrement skill cooldown end of round
            //powerstone add +1 to keep cooldown unchanged
            user.decrementSkillCooldown();

            // only increment if game isnt over, else victory shows wrong round count
            if (!gameOver) roundNumber++;
        }

        displayGameOverScreen();
        return playerWon;
    }

    //update status effect for combatant end of round
    //decrement durations, trigger onExpire() to rever stat change
    //remove expired effect
    private void handleEffectDecrement(Combatant combatant) {
        List<Effects> expiredEffects = new ArrayList<>();
        for (Effects effect : new ArrayList<>(combatant.getStatusEffects())) {
            effect.decrementDuration();
            if (effect.hasExpired()) {
                expiredEffects.add(effect);
                effect.onExpire(combatant);
            }
        }
        //remove all expired effect
        combatant.getStatusEffects().removeAll(expiredEffects);
    }

    //check for victory or defeat
    //defeat player hp is 0
    //victory is all enemy and backup wave defeated
    private void checkGameStatus() {
        if (user.getHp() <= 0) {
            gameOver = true;
            playerWon = false;
        } else if (activeEnemies.isEmpty() && backupEnemies.isEmpty()) {
            gameOver = true;
            playerWon = true;
        }
    }

    //handle spawn of next wave at end of round
    //if active enemy are dead, move all backups to the active list and icnrement wave count
    //backup list is cleared so that max is two wave
    private void handleBackupWave() {
        if (!backupEnemies.isEmpty() && activeEnemies.isEmpty()) {
            System.out.println("\n=== WAVE " + waveNumber + " CLEARED ===");
            waveNumber++;
            activeEnemies.addAll(backupEnemies);
            backupEnemies.clear();
            System.out.println("=== WAVE " + waveNumber + " INCOMING ===");
            for (Combatant enemy : activeEnemies) {
                System.out.println("  " + enemy.getName() + "  HP: " + enemy.getHp() + "/" + enemy.getMaxHp());
            }
        }
    }

    //display victory or defeat screen
    //pass final round count and remaining enemy to final round game stats
    private void displayGameOverScreen() {
        if (playerWon) {
            gameplayUI.displayVictory(user, roundNumber);
        } else {
            gameplayUI.displayDefeat(user, roundNumber, activeEnemies.size());
        }
    }
}
