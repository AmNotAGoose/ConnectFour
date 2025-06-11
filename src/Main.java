import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static Integer[][] board = new Integer[6][7];
    static int boardWidth = board[0].length;
    static int boardHeight = board.length;

    static String[] playerCharacters = {" ", "X", "O"};

    public static void displayBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(" " + playerCharacters[board[i][j]] + " |");
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

    public static boolean checkForMatches() {
        // check horizontally
        // check vertically
        // check diagonally

        return false;
    }

    public static int getValueAtGrid(int row, int column) {

        return 0;
    }
    
    public static void placePiece(int column, int piece) {
//        board[0][0] = 1;
//        board[0][3] = 1;
        int row;

        System.out.println(board.length + " " + board[0].length);

        for (row = boardHeight - 1; row >= 0; row--) {
            if (board[row][column] == 0) {
                System.out.println("Aaron");
                break;
            }
        }

//        row++;
        System.out.println(row + " " + column);
        board[row][column] = piece;
    }

    public static void gamemodePvP() {
        resetBoard();

        int xScore = 0;
        int oScore = 0;

        boolean hasWon = false;

        int curPlayer = 1;

        while (!hasWon) {
            curPlayer = curPlayer % 2 + 1;

            System.out.println("Player " + playerCharacters[curPlayer] + " to move (Enter the column number): ");

            int playerColumnSelection = sc.nextInt();

            if ((0 <= playerColumnSelection && playerColumnSelection < boardWidth)) {
                placePiece(playerColumnSelection, curPlayer);
            }

            displayBoard();
        }
    }

    public static void main(String[] args) {
//        displayBoard();
        gamemodePvP();
    }
}


