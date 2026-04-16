//InputHelper: Brennen
package Boundary;

import java.util.InputMismatchException;
import java.util.Scanner;

// utility class to handle all console input for the app
// centralizes scanner usage and provides validation to prevent game crashes
public class InputHelper {

    // we use a single shared scanner for the entire game
    // early on creating new scanner(system.in) in different ui classes caused them to 
    // fight over the input stream leading to skipped inputs and lag so this singleton fixes that
    private static final Scanner SHARED_SCANNER = new Scanner(System.in);

    public static Scanner getScanner() {
        return SHARED_SCANNER;
    }

    public static int getUserChoice(Scanner scanner, int min, int max, String errorMessage) {
        // wrapped in a while(true) and try-catch. if the user types a letter instead of a number
        // scanner.nextline() flushes the bad token out of the buffer so the game doesnt crash 
        // with an inputmismatchexception and it just asks them again
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the leftover newline after nextint()

                if (choice < min || choice > max) {
                    System.out.println(errorMessage);
                    continue;
                }
                return choice;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer only.");
                scanner.nextLine(); // clear the bad token so we can try again
            }
        }
    }
}
