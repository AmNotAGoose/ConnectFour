import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static int[][] board = new int[6][7];
    static int boardWidth = board[0].length;
    static int boardHeight = board.length;

    static String[] playerCharacters = {" ", "X", "O"};

    static int[] globalScore = {0, 0};

    static ArrayList<String> afterDisplayBoardMessages = new ArrayList<>();

    public static void displayBoard() {
        for (int i = 1; i <= boardWidth; i++) {
            System.out.print("  " + i + " ");
        }
        System.out.println();
        for (int[] integers : board) {
            System.out.print("|");
            for (int j = 0; j < boardWidth; j++) {
                System.out.print(" " + playerCharacters[integers[j]] + " |");
            }
            System.out.println();
        }

        for (String message : afterDisplayBoardMessages) {
            System.out.println(message);
        }

        afterDisplayBoardMessages = new ArrayList<>();
    }

    public static void displayWinMessage(int winner, int[] score) {
        afterDisplayBoardMessages.add("Player " + playerCharacters[winner] + " won the game. ");
        afterDisplayBoardMessages.add("Current game score: " + playerCharacters[1] + ": " + score[0] + ", " + playerCharacters[2] + ": " + score[1]);
        afterDisplayBoardMessages.add("Lifetime score: " + playerCharacters[1] + ": " + globalScore[0] + ", " + playerCharacters[2] + ": " + globalScore[1]);
    }

    public static void resetBoard() {
        // initialize board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
    }

    public static boolean isInBounds(int row, int column) {
        // check if the row and column is between 0 (inclusive) and the board height (not inclusive).
        // this considers selected columns outside of the board width, and also overflowing pieces
        return 0 <= row && row < boardHeight && 0 <= column && column < boardWidth;
    }

    public static boolean isFull(int column) {
        // checks if the column is full
        return board[0][column] != 0;
    }

    public static int checkForWinCondition() {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                int cell = board[row][column];

                if (!(cell == 0)) {
                    // check horizontally
                    if (isInBounds(row, column + 3)) {
                        if (cell == board[row][column + 1] && cell == board[row][column + 2] && cell == board[row][column + 3]) {
                            return cell;
                        }
                    }

                    // check vertically
                    if (isInBounds(row + 3, column)) {
                        if (cell == board[row + 1][column] && cell == board[row + 2][column] && cell == board[row + 3][column]) {
                            return cell;
                        }
                    }

                    // check diagonal-right
                    if (isInBounds(row + 3, column + 3)) {
                        if (cell == board[row + 1][column + 1] && cell == board[row + 2][column + 2] && cell == board[row + 3][column + 3]) {
                            return cell;
                        }
                    }


                    // check diagonal-left
                    if (isInBounds(row + 3, column - 3)) {
                        if (cell == board[row + 1][column - 1] && cell == board[row + 2][column - 2] && cell == board[row + 3][column - 3]) {
                            return cell;
                        }
                    }
                }
            }
        }

        return 0;
    }

    public static boolean placePiece(int column, int piece) {
        int row = boardHeight - 1;

        if (!isInBounds(row, column)) { // if the row is not in bounds, return false early
            afterDisplayBoardMessages.add("The column is out of bounds.");
            return false;
        }

        for (row = boardHeight - 1; row >= 0; row--) {
            if (board[row][column] == 0) {
                break;
            }
        }

        if (!isInBounds(row, column)) { // if the row is not in bounds, return false early
            afterDisplayBoardMessages.add("The column is already full.");
            return false;
        }

        board[row][column] = piece;

        return true;
    }

    public static boolean promptPlayAgain() {
        sc.nextLine(); // hack fix

        while (true) {
            System.out.print("Would you like to play again? (Y / N): ");

            String option = sc.nextLine();
            if (option.toLowerCase().equals("y")) {
                return true;
            } else if (option.toLowerCase().equals("n")) {
                return false;
            } else {
                System.out.println("Invalid choice. Pick either Y for yes or N for no. ");
            }
        }
    }

    // All output from agents should be sanitized

    public static int userAgent() {
        int selection = sc.nextInt() - 1;
        return 0;
    }

    public static int randomAgent() {
        return 0;
    }

    public static int smartAgent() {
        return 0;
    }

    public static void gamemodePvP() {
        boolean hasQuit = false;
        int[] score = {0, 0};

        while (!hasQuit) {
            resetBoard();

            int curPlayer = 1;

            boolean hasWon = false;

            while (!hasWon) {
                System.out.println("Player " + playerCharacters[curPlayer] + " to move (Enter the column number): ");

                int playerColumnSelection = sc.nextInt() - 1;

                if (placePiece(playerColumnSelection, curPlayer)) {
                    int winningPlayer = checkForWinCondition();

                    if (winningPlayer != 0) {
                        score[winningPlayer - 1] += 1;
                        globalScore[winningPlayer - 1] += 1;
                        displayWinMessage(winningPlayer, score);

                        hasWon = true;
                    }

                    curPlayer = curPlayer % 2 + 1;
                } else {
                    afterDisplayBoardMessages.add("Invalid column selection!");
                }

                displayBoard();
            }

            if (!promptPlayAgain()) {
                System.out.println("Returning to main menu.");
                hasQuit = true;
            }
        }


    }

    public static void main(String[] args) {
        gamemodePvP();
    }
}


