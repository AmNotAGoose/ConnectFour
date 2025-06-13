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

    public static boolean checkForWinCondition() {

        // check horizontally
        for (int[] row : board) {
            int totalHorizontalInARow = 1;
            int prevCell = 0;

            for (int cell : row) {
                if (prevCell != 0 && prevCell == cell) {
                    totalHorizontalInARow++;
                } else {
                    totalHorizontalInARow = 1;
                }

                if (totalHorizontalInARow == 4) {
                    return true;
                }

                prevCell = cell;
            }
        }

//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[0].length; j++) {
//                // check horizontally
//
//                // check vertically
//
//                // check diagonally
//            }
//        }

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


