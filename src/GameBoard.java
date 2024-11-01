//GROUP MEMBERS:
// MELINA BEHZADI NEJAD - 101447858
// MOBINASADAT ZARGARY - 101472495

public class GameBoard {

    public final char[][] board;
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;

    public GameBoard() {
        board = new char[ROWS][COLUMNS];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = '.';
            }
        }
    }

    public boolean isColumnFull(int column) {
        return board[0][column] != '.';
    }

    public boolean makeMove(int column, char symbol) {
        if (column < 0 || column >= COLUMNS) {
            System.out.println("Invalid column. Please select a column between 1 and " + COLUMNS + ".");
            return false;
        }

        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column] == '.') {
                board[i][column] = symbol;
                return true;
            }
        }
        return false;
    }


    public boolean undoMove(int column) {

        for (int i = 0; i < ROWS; i++) {
            if (board[i][column] != '.') {
                board[i][column] = '.';
                return true; // Successfully removed the topmost symbol.
            }
        }
        return false;
    }





    public void displayBoard() {

        System.out.println("\n    1   2   3   4   5   6   7");
        System.out.println("  +---+---+---+---+---+---+---+");
        for (int i = 0; i < ROWS; i++) {
            System.out.print("  |");
            for (int j = 0; j < COLUMNS; j++) {

                char displaySymbol = board[i][j] == '.' ? ' ' : board[i][j];
                System.out.print(" " + displaySymbol + " |");
            }
            System.out.println("\n  +---+---+---+---+---+---+---+");
        }
    }


    public boolean checkForWin(char symbol) {

        return checkHorizontalWin(symbol) || checkVerticalWin(symbol) || checkDiagonalWin(symbol);
    }

    private boolean checkHorizontalWin(char symbol) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if (board[i][j] == symbol && board[i][j + 1] == symbol &&
                        board[i][j + 2] == symbol && board[i][j + 3] == symbol) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean checkVerticalWin(char symbol) {
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == symbol && board[i + 1][j] == symbol &&
                        board[i + 2][j] == symbol && board[i + 3][j] == symbol) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalWin(char symbol) {
        //Ascending
        for (int i = 3; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if (board[i][j] == symbol && board[i - 1][j + 1] == symbol &&
                        board[i - 2][j + 2] == symbol && board[i - 3][j + 3] == symbol) {
                    return true;
                }
            }
        }
        //Descending
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if (board[i][j] == symbol && board[i + 1][j + 1] == symbol &&
                        board[i + 2][j + 2] == symbol && board[i + 3][j + 3] == symbol) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean checkForDraw() {
        for (int j = 0; j < COLUMNS; j++) {
            if (board[0][j] == '.') {
                return false;
            }
        }
        return true;
    }




    private int evaluateLines(char aiSymbol) {
        int score = 0;
        char opponentSymbol = (aiSymbol == 'R') ? 'Y' : 'R';

        // Evaluate horizontal lines
        score += evaluateHorizontalLines(aiSymbol, opponentSymbol);

        // Evaluate vertical lines
        score += evaluateVerticalLines(aiSymbol, opponentSymbol);

        // Evaluate diagonal lines
        score += evaluateDiagonalLines(aiSymbol, opponentSymbol);

        return score;
    }


    private int evaluateHorizontalLines(char aiSymbol, char opponentSymbol) {
        int score = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                int aiCount = 0;
                int opponentCount = 0;
                for (int offset = 0; offset < 4; offset++) {
                    if (board[row][col + offset] == aiSymbol) aiCount++;
                    else if (board[row][col + offset] == opponentSymbol) opponentCount++;
                }
                score += scorePattern(aiCount, opponentCount);
            }
        }
        return score;
    }

    private int evaluateVerticalLines(char aiSymbol, char opponentSymbol) {
        int score = 0;
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                int aiCount = 0;
                int opponentCount = 0;
                for (int offset = 0; offset < 4; offset++) {
                    if (board[row + offset][col] == aiSymbol) aiCount++;
                    else if (board[row + offset][col] == opponentSymbol) opponentCount++;
                }
                score += scorePattern(aiCount, opponentCount);
            }
        }
        return score;
    }

    private int evaluateDiagonalLines(char aiSymbol, char opponentSymbol) {
        int score = 0;
        // Ascending Diagonals
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                int aiCount = 0;
                int opponentCount = 0;
                for (int offset = 0; offset < 4; offset++) {
                    if (board[row - offset][col + offset] == aiSymbol) aiCount++;
                    else if (board[row - offset][col + offset] == opponentSymbol) opponentCount++;
                }
                score += scorePattern(aiCount, opponentCount);
            }
        }

        // Descending Diagonals
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                int aiCount = 0;
                int opponentCount = 0;
                for (int offset = 0; offset < 4; offset++) {
                    if (board[row + offset][col + offset] == aiSymbol) aiCount++;
                    else if (board[row + offset][col + offset] == opponentSymbol) opponentCount++;
                }
                score += scorePattern(aiCount, opponentCount);
            }
        }
        return score;
    }
    private int scorePattern(int aiCount, int opponentCount) {
        // Modify these values based on your game's balance needs
        if (opponentCount == 0) {
            if (aiCount == 4) return 100; // Win
            else if (aiCount == 3) return 5; // Potential win
            else if (aiCount == 2) return 2; // Setup
        } else if (aiCount == 0) { // Blocks
            if (opponentCount == 3) return -4; // Prevent opponent win
        }
        return 0; // Neutral or not useful
    }

    public int evaluateBoardState(char aiSymbol) {
        int score = 0;


        score += evaluateLines(aiSymbol);



        return score;
    }

}
