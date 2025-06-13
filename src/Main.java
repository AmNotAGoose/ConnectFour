import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static int[][] board = new int[6][7];
    static int boardWidth = board[0].length;
    static int boardHeight = board.length;

    static String[] playerCharacters = {" ", "X", "O"};

    public static void displayBoard() {
        for (int[] integers : board) {
            System.out.print("|");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(" " + playerCharacters[integers[j]] + " |");
            }
            System.out.println();
        }
    }

    public static void resetBoard() {
        // initialize board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
    }

    public static boolean isInBounds(int row, int column) { // i, j NOT j, i
        return 0 <= row && row < boardHeight && 0 <= column && column < boardWidth;
    }

    public static boolean checkForWinCondition() {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                int cell = board[row][column];

                if (!(cell == 0)) {
                    // check horizontally
                    if (isInBounds(row, column + 3)) {
                        if (cell == board[row][column + 1] && cell == board[row][column + 2] && cell == board[row][column + 3]) {
                            System.out.println("Hawk horizontal");
                            return true;
                        }
                    }

                    // check vertically
                    if (isInBounds(row + 3, column)) {
                        if (cell == board[row + 1][column] && cell == board[row + 2][column] && cell == board[row + 3][column]) {
                            System.out.println("Hawk vertical");
                            return true;
                        }
                    }

                    // check diagonally
                    if (isInBounds(row + 3, column + 3)) {
                        if (cell == board[row + 1][column + 1] && cell == board[row + 2][column + 2] && cell == board[row + 3][column + 3]) {
                            System.out.println("Hawk diagonal");
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static int getValueAtGrid(int row, int column) {

        return 0;
    }
    
    public static void placePiece(int column, int piece) {
        int row;

        for (row = boardHeight - 1; row >= 0; row--) {
            if (board[row][column] == 0) {
                break;
            }
        }

        board[row][column] = piece;
    }

    public static void gamemodePvP() {
        resetBoard();

        int xScore = 0;
        int oScore = 0;

        boolean hasWon = false;

        int curPlayer = 1;

        while (!hasWon) {
            System.out.println("Player " + playerCharacters[curPlayer] + " to move (Enter the column number): ");

            int playerColumnSelection = sc.nextInt();

            if ((0 <= playerColumnSelection && playerColumnSelection < boardWidth)) {
                placePiece(playerColumnSelection, curPlayer);

                checkForWinCondition();
                displayBoard();

                curPlayer = curPlayer % 2 + 1;
            } else {
                displayBoard();
                System.out.println("Invalid placement!");
            }
        }
    }

    public static void main(String[] args) {
//        displayBoard();
        gamemodePvP();
    }
}


