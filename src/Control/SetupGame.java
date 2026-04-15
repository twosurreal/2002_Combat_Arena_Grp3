package Control;

import Boundary.GameStartScreen;
import Entity.Combatant.Combatant;
import Entity.Combatant.Warrior;
import Entity.Combatant.Wizard;
import Entity.Items.Item;
import Entity.Items.Potion;
import Entity.Items.Powerstone;
import Entity.Items.Smokebomb;
import Entity.Level;

import Entity.EnemyActions.EnemyBasicAttack;
import Entity.TurnOrder.SpeedBasedTurnOrder;
import Boundary.GamePlayScreen;

import java.util.ArrayList;
import java.util.List;

public class SetupGame {
    private final GameStartScreen gameStartScreen;

    public SetupGame() {
        gameStartScreen = new GameStartScreen();
    }

    public void initialiseGame() {
        gameStartScreen.displayLoadingScreen();

        int combatantChoice = gameStartScreen.displayCharacterScreen();
        List<Integer> itemChoices = gameStartScreen.displayItemScreen();
        int levelChoice = gameStartScreen.displayLevelScreen();

        runGame(combatantChoice, itemChoices, levelChoice);
    }

    private void runGame(int combatantChoice, List<Integer> itemChoices, int levelChoice) {
        Combatant myCombatant = getCombatantFromChoice(combatantChoice);

        List<Item> myItems = new ArrayList<>();
        for (int choice : itemChoices) {
            myItems.add(getItemFromChoice(choice));
        }

        Level myLevel = new Level(levelChoice);

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

        if (playerWon) {
            gameVictory(combatantChoice, itemChoices, levelChoice);
        } else {
            gameDefeat(combatantChoice, itemChoices, levelChoice);
        }
    }

    private void gameVictory(int combatantChoice, List<Integer> itemChoices, int levelChoice) {
        // Victory screen already displayed by BattleEngine via GamePlayScreen
        int replayChoice = gameStartScreen.displayReplayScreen();
        handleReplay(replayChoice, combatantChoice, itemChoices, levelChoice);
    }

    private void gameDefeat(int combatantChoice, List<Integer> itemChoices, int levelChoice) {
        int replayChoice = gameStartScreen.displayReplayScreen();
        handleReplay(replayChoice, combatantChoice, itemChoices, levelChoice);
    }

    private void handleReplay(int replayChoice, int combatantChoice, List<Integer> itemChoices, int levelChoice) {
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

    private Combatant getCombatantFromChoice(int combatantChoice) {
        return switch (combatantChoice) {
            case 1 -> new Warrior();
            case 2 -> new Wizard();
            default -> null;
        };
    }

    private Item getItemFromChoice(int itemChoice) {
        return switch (itemChoice) {
            case 1 -> new Potion();
            case 2 -> new Powerstone();
            case 3 -> new Smokebomb();
            default -> null;
        };
    }
}
