//GROUP MEMBERS:
// MELINA BEHZADI NEJAD - 101447858
// MOBINASADAT ZARGARY - 101472495

import java.util.Scanner;

public class Game {
    private final GameBoard board;
    private Player player1;
    private Player player2;
    private final boolean isSinglePlayer;
    private final Scanner scanner = new Scanner(System.in);

    public Game(boolean isSinglePlayer) {
        this.isSinglePlayer = isSinglePlayer;
        this.board = new GameBoard();
        initializePlayers();
        if (isSinglePlayer) {
            decideFirstMove();
        }
    }

    private void initializePlayers() {

        String playerName1 = getValidatedName("Enter Player 1's name: ");
        char symbol1 = chooseSymbol(playerName1 + ", choose your symbol (R for Red, Y for Yellow): ");


        player1 = new Player(playerName1, symbol1);

        //automatically determine and assign the opposite symbol for Player 2 or AI.
        char oppositeSymbol = (symbol1 == 'R') ? 'Y' : 'R';

        if (isSinglePlayer) {
            //in single-player mode, Player 2 is the AI, automatically assigned the opposite symbol.
            player2 = new Player("AI", oppositeSymbol);
        } else {
            //in two-player mode, prompt for Player 2's name and assign the opposite symbol.
            String playerName2 = getValidatedName("Player 2, enter your name: ");
            player2 = new Player(playerName2, oppositeSymbol);
        }
    }


    private String getValidatedName(String prompt) {
        System.out.print(prompt);
        String name = scanner.nextLine().trim();

        while (name.isEmpty() || name.matches(".*\\d.*")) {
            System.out.println("Invalid name. Names cannot be empty or contain numbers. Please enter a valid name:");
            name = scanner.nextLine().trim();
        }
        return name;
    }




    private String getPlayerName(String prompt) {
        System.out.print(prompt);
        String name = scanner.nextLine().trim();


        while (name.isEmpty() || name.matches(".*\\d+.*")) {
            System.out.println("Invalid name. Names cannot contain numbers. Please enter a valid name:");
            name = scanner.nextLine().trim();
        }
        return name;
    }




    private char chooseSymbol(String prompt) {
        System.out.println(prompt);

        String input = scanner.nextLine().trim().toUpperCase();

        while (!"R".equals(input) && !"Y".equals(input)) {
            System.out.println("Oops! That's not a valid symbol.");
            System.out.println("Please choose 'R' for Red or 'Y' for Yellow:");
            input = scanner.nextLine().trim().toUpperCase();
        }

        return input.charAt(0);
    }

    private void decideFirstMove() {
        System.out.println("Do you want to go first? (1: Yes, 2: No): ");
        int choice = getIntInput(1, 2);
        if (choice == 2) {
            makeAIMove();
        }
    }

    int minimax(GameBoard board, int depth, boolean isMaximizingPlayer, char aiSymbol, char humanSymbol, int alpha, int beta) {

        if (board.checkForWin(aiSymbol)) {
            return 1000; //favorable outcome for AI
        } else if (board.checkForWin(humanSymbol)) {
            return -1000; //unfavorable outcome for AI
        } else if (board.checkForDraw() || depth == 0) {

            return board.evaluateBoardState(aiSymbol);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < GameBoard.COLUMNS; col++) {
                if (!board.isColumnFull(col)) {
                    board.makeMove(col, aiSymbol); //AI makes move
                    int eval = minimax(board, depth - 1, false, aiSymbol, humanSymbol, alpha, beta);
                    board.undoMove(col);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) break; //alpha-beta pruning
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < GameBoard.COLUMNS; col++) {
                if (!board.isColumnFull(col)) {
                    board.makeMove(col, humanSymbol); //human makes move
                    int eval = minimax(board, depth - 1, true, aiSymbol, humanSymbol, alpha, beta);
                    board.undoMove(col);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break;
                }
            }
            return minEval;
        }
    }




    private void makeAIMove() {
        System.out.println("\nAI is contemplating its next move...");

        int bestScore = Integer.MIN_VALUE;
        int bestColumn = -1;
        char aiSymbol = player2.getSymbol();
        char humanSymbol = player1.getSymbol();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        //iterate through all possible columns to find the best move
        for (int col = 0; col < GameBoard.COLUMNS; col++) {
            if (!board.isColumnFull(col)) {

                board.makeMove(col, aiSymbol);

                //evaluate the board state from this move using the updated Minimax algorithm
                int score = minimax(board, 5, false, aiSymbol, humanSymbol, alpha, beta); // Assuming a search depth of 5 for more strategic depth

                //undo the move to restore the board state
                board.undoMove(col);

                if (score > bestScore) {
                    bestScore = score;
                    bestColumn = col;
                }
            }
        }


        if (bestColumn != -1) {
            board.makeMove(bestColumn, aiSymbol);
            System.out.println("AI confidently places its symbol in column " + (bestColumn + 1) + ".");
        } else {
            System.out.println("AI could not find a valid move. This shouldn't happen if implemented correctly.");
        }
    }






    public void start() {
        Player currentPlayer = player1;

        System.out.println("\nLet's get started! May the best player win!");
        System.out.println("============================================");

        while (true) {
            if (!"AI".equals(currentPlayer.getName())) {
                board.displayBoard();
                System.out.println("\n||| " + currentPlayer.getName() + "'s turn |||");
                System.out.println("Please enter a number between 1 - 7 " + currentPlayer.getName() + " :");
                int column = getIntInput(1, 7) - 1;
                while (!board.makeMove(column, currentPlayer.getSymbol())) {
                    System.out.println("Oops! That column is full. Try another one:");
                    column = getIntInput(1, 7) - 1;
                }
            } else {
                System.out.println("AI is thinking...");
                makeAIMove();
            }

            if (board.checkForWin(currentPlayer.getSymbol())) {
                board.displayBoard();
                System.out.println("+--------------------------------------------------------------------------+");
                System.out.println("              Congratulations, " + currentPlayer.getName() + "! You've won!       ");
                System.out.println("+--------------------------------------------------------------------------+");
                return;
            } else if (board.checkForDraw()) {
                board.displayBoard();
                System.out.println("+------------------------------------+");
                System.out.println("|   It's a draw! Well played both!   |");
                System.out.println("+------------------------------------+");
                return;
            }

            currentPlayer = switchPlayer(currentPlayer);
        }
    }



    private Player switchPlayer(Player currentPlayer) {
        return currentPlayer == player1 ? player2 : player1;
    }

    private int getIntInput(int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            try {
                String input = scanner.nextLine().trim();

                choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                } else {

                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ":");
                }
            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a valid number:");
            }
        }
        return choice;
    }
}
