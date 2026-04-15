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

import java.util.ArrayList;
import java.util.List;

public class SetupGame {
    private final GameStartScreen gameStartScreen;

    public SetupGame() {
        gameStartScreen = new GameStartScreen();
    }

    void main(String[] args){
        initialiseGame();
    }

    public void initialiseGame() {
        System.out.println("\n=== Turn Based Combat Arena ===");

        // select character, items and level
        int combatantChoice = gameStartScreen.displayCharacterScreen();
        List<Integer> itemChoices = gameStartScreen.displayItemScreen();
        int levelChoice = gameStartScreen.displayLevelScreen();

        runGame(combatantChoice, itemChoices, levelChoice);
    }

    private void runGame(int combatantChoice, List<Integer> itemChoices, int levelChoice) {

        // create objects from integer choices
        Combatant myCombatant = getCombatantFromChoice(combatantChoice);

        List<Item> myItems = new ArrayList<>();
        for (int choice : itemChoices) {
            myItems.add(getItemFromChoice(choice));
        }

        Level myLevel = new Level(levelChoice);

        // start gameplay
        BattleEngine gamePlayBackend = new BattleEngine(
                myCombatant,
                myItems,
                myLevel
        );

        System.out.println("\nStarting game");
        gamePlayBackend.playGameLoop();

        // handle game over once loop ends
        gameOver(combatantChoice, itemChoices, levelChoice);
    }

    private void gameOver(int combatantChoice, List<Integer> itemChoices, int levelChoice) {
        int replayChoice = gameStartScreen.displayReplayScreen();

        switch (replayChoice) {
            case 1 -> {
                System.out.println("!!! Replaying game with same settings");
                runGame(combatantChoice, itemChoices, levelChoice);
            }
            case 2 -> {
                System.out.println("!!! Starting new game");
                initialiseGame();
            }
            case 3 -> {
                System.out.println("!!! Exiting game");
                System.exit(0);
            }
            default -> {
                System.exit(0);
            }
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
