package Boundary;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    public static int getUserChoice(Scanner scanner, int min, int max, String errorMessage) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice < min || choice > max) {
                    System.out.println(errorMessage);
                    continue;
                }
                return choice;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer only.");
                scanner.nextLine();
            }
        }
    }
}
