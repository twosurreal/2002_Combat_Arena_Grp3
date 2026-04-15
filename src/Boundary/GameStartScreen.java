package Boundary;

import Entity.Combatant.Combatant;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameStartScreen {
    private final Scanner scanner;

    public GameStartScreen() {
        scanner = new Scanner(System.in);
    }

    public int displayCharacterScreen() {
        int characterChoice;
        while (true) {
            System.out.println("\nSelect Character: ");
            System.out.println("1. Warrior");
            System.out.println("2. Wizard");
            characterChoice = scanner.nextInt();

            if (characterChoice < 1 || characterChoice > 2) {
                System.out.println("Enter valid choice.");
                continue;
            }
            break;
        }
        return characterChoice;
    }

    public List<Integer> displayItemScreen() {
        System.out.println("\nSelect Items: ");
        System.out.println("1. Potion");
        System.out.println("2. Powerstone");
        System.out.println("3. Smokebomb");
        List<Integer> itemChoices = new ArrayList<>();
        for (int i = 0; i < Combatant.max_items; i++) {
            int itemChoice;
            while (true) {
                itemChoice = scanner.nextInt();
                if (itemChoice < 1 || itemChoice > 3) {
                    System.out.println("Enter valid choice.");
                    continue;
                }
                break;
            }
            itemChoices.add(itemChoice);
        }
        return itemChoices;
    }

    public int displayLevelScreen() {
        int levelChoice;
        while (true) {
            System.out.println("\nSelect level: ");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");
            levelChoice = scanner.nextInt();

            if (levelChoice < 1 || levelChoice > 3) {
                System.out.println("Enter valid choice.");
                continue;
            }

            break;
        }
        return levelChoice;
    }

    public int displayReplayScreen() {
        int replayChoice;
        while (true) {
            System.out.println("\nSelect action: ");
            System.out.println("1. Replay with same settings");
            System.out.println("2. Start new game");
            System.out.println("3. Exit");
            replayChoice = scanner.nextInt();

            if (replayChoice < 1 || replayChoice > 3) {
                System.out.println("Enter valid choice.");
                continue;
            }

            break;
        }
        return replayChoice;
    }
}
