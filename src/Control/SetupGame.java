// SetupGame.java: Joshua
// manages game setup, start loop and handle replay menu

package Control;

import Boundary.GameStartScreen;
import Boundary.GamePlayScreen;
import Entity.Combatant.Combatant;
import Entity.Combatant.Warrior;
import Entity.Combatant.Wizard;
import Entity.EnemyActions.EnemyBasicAttack;
import Entity.Items.Item;
import Entity.Items.Potion;
import Entity.Items.Powerstone;
import Entity.Items.Smokebomb;
import Entity.Level;
import Entity.TurnOrder.SpeedBasedTurnOrder;

import java.util.ArrayList;
import java.util.List;

public class SetupGame {

    private final GameStartScreen gameStartScreen;

    public SetupGame() {
        gameStartScreen = new GameStartScreen();
    }

    //main entry point for main.java
    public void initialiseGame() {
        gameStartScreen.displayLoadingScreen();

        //ask player for character
        int combatantChoice = gameStartScreen.displayCharacterScreen();

        //ask player select items
        List<Integer> itemChoices = gameStartScreen.displayItemScreen();

        //ask player select difficulty level
        int levelChoice = gameStartScreen.displayLevelScreen();

        runGame(combatantChoice, itemChoices, levelChoice);
    }

    //sets up battle with interface so replay can skip menu
    private void runGame(int combatantChoice, List<Integer> itemChoices, int levelChoice) {
        Combatant myCombatant = getCombatantFromChoice(combatantChoice);

        List<Item> myItems = new ArrayList<>();
        for (int choice : itemChoices) {
            myItems.add(getItemFromChoice(choice));
        }

        //mylevel hold enemy waves
        Level myLevel = new Level(levelChoice);

        // setup BattleEngine and it use screen reference instead of using own ui
        BattleEngine gamePlayBackend = new BattleEngine(
                myCombatant,
                myItems,
                myLevel,
                new EnemyBasicAttack(),
                new SpeedBasedTurnOrder(),
                new ActionChoiceController(),
                new GamePlayScreen()
        );

        System.out.println("\nStarting game...");
        boolean playerWon = gamePlayBackend.playGameLoop();

        //run game loop and return true if player win
        if (playerWon) {
            gameVictory(combatantChoice, itemChoices, levelChoice);
        } else {
            gameDefeat(combatantChoice, itemChoices, levelChoice);
        }
    }

    //replay prompt after player win
    private void gameVictory(int combatantChoice, List<Integer> itemChoices, int levelChoice) {
        // victory screen already shown by BattleEngine before we get here
        int replayChoice = gameStartScreen.displayReplayScreen();
        handleReplay(replayChoice, combatantChoice, itemChoices, levelChoice);
    }

    //replay prompt after player lose
    private void gameDefeat(int combatantChoice, List<Integer> itemChoices, int levelChoice) {
        int replayChoice = gameStartScreen.displayReplayScreen();
        handleReplay(replayChoice, combatantChoice, itemChoices, levelChoice);
    }

    //handle replay menu
    private void handleReplay(int replayChoice, int combatantChoice,
                              List<Integer> itemChoices, int levelChoice) {
        switch (replayChoice) {
            case 1 -> {
                System.out.println("Replaying with same settings...");
                runGame(combatantChoice, itemChoices, levelChoice);
            }
            case 2 -> {
                System.out.println("Starting new game...");
                initialiseGame();
            }
            case 3 -> {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
            default -> System.exit(0);
        }
    }

    //turns the choice number into character
    private Combatant getCombatantFromChoice(int choice) {
        return switch (choice) {
            case 1 -> new Warrior();
            case 2 -> new Wizard();
            default -> null; //
        };
    }
    //turn choice number into item choice
    private Item getItemFromChoice(int choice) {
        return switch (choice) {
            case 1 -> new Potion();
            case 2 -> new Powerstone();
            case 3 -> new Smokebomb();
            default -> null;
        };
    }
}
