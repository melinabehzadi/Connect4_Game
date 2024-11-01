//GROUP MEMBERS:
// MELINA BEHZADI NEJAD - 101447858
// MOBINASADAT ZARGARY - 101472495


import java.util.InputMismatchException;
import java.util.Scanner;

public class GameMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //game explanation
        System.out.println("+-------------------------------------------------+");
        System.out.println("|             Welcome to Connect 4!               |");
        System.out.println("+-------------------------------------------------+");
        System.out.println();
        System.out.println("Connect 4 is a classic two-player board game where strategy,");
        System.out.println("skill, and a bit of luck are key. Players choose a color - Yellow (Y) or");
        System.out.println("Red (R), and take turns dropping colored discs into a grid.");
        System.out.println();
        System.out.println("The game is played on a vertical board with seven columns and six rows.");
        System.out.println("Players aim to be the first to form a line of four of their own discs either");
        System.out.println("horizontally, vertically, or diagonally.");
        System.out.println();
        System.out.println("Ready to outsmart your opponent and connect four? Let's get started!");
        System.out.println("===================================================");

        // Game mode selection
        System.out.println("\nHow would you like to play?");
        System.out.println("1 - Play against the AI (Artificial Intelligence)");
        System.out.println("2 - Two-player mode (Challenge a friend)\n");
        System.out.print("Please enter your choice (1 or 2): ");

        int choice = getIntInput(scanner, 1, 2);


        System.out.println(choice == 1 ? "\nYou've chosen to play against the AI. Good luck!" : "\nTwo-player mode selected. May the best player win!");


        if (choice == 1) {
            startSinglePlayerGame();
        } else {
            startTwoPlayerGame();
        }
    }

    private static int getIntInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Invalid choice. Please select 1 or 2:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2):");
                scanner.next();
            }
        }
    }

    public static void startSinglePlayerGame() {
        Game game = new Game(true);
        game.start();
    }

    private static void startTwoPlayerGame() {
        Game game = new Game(false);
        game.start();
    }
}
