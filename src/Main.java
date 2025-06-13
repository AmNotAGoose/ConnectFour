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

        for (int[] row : board) {
            System.out.print("|");
            for (int j = 0; j < boardWidth; j++) {
                System.out.print(" " + playerCharacters[row[j]] + " |");
            }
            System.out.println();
        }

        for (String message : afterDisplayBoardMessages) {
            System.out.println(message);
        }

        afterDisplayBoardMessages = new ArrayList<>();
    }

    public static void displayWinMessage(int winner, int[] score) {
        if (winner == 0) {
            afterDisplayBoardMessages.add("The game ended in a tie.");
        } else {
            afterDisplayBoardMessages.add("Player " + playerCharacters[winner] + " won the game.");
        }
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

    public static boolean isFull(int[][] curBoard) {
        // checks if the board is full
        boolean full = true;

        for (int[] row : curBoard) {
            for (int cell : row) {
                if (cell == 0) {
                    full = false;
                    break;
                }
            }
        }

        return full;
    }

    public static int checkForWinCondition(int[][] curBoard) {
        for (int row = 0; row < curBoard.length; row++) {
            for (int column = 0; column < curBoard[0].length; column++) {
                int cell = curBoard[row][column];

                if (!(cell == 0)) {
                    // check horizontally
                    if (isInBounds(row, column + 3)) {
                        if (cell == curBoard[row][column + 1] && cell == curBoard[row][column + 2] && cell == curBoard[row][column + 3]) {
                            return cell;
                        }
                    }

                    // check vertically
                    if (isInBounds(row + 3, column)) {
                        if (cell == curBoard[row + 1][column] && cell == curBoard[row + 2][column] && cell == curBoard[row + 3][column]) {
                            return cell;
                        }
                    }

                    // check diagonal-right
                    if (isInBounds(row + 3, column + 3)) {
                        if (cell == curBoard[row + 1][column + 1] && cell == board[row + 2][column + 2] && cell == curBoard[row + 3][column + 3]) {
                            return cell;
                        }
                    }


                    // check diagonal-left
                    if (isInBounds(row + 3, column - 3)) {
                        if (cell == curBoard[row + 1][column - 1] && cell == curBoard[row + 2][column - 2] && cell == curBoard[row + 3][column - 3]) {
                            return cell;
                        }
                    }
                }
            }
        }

        return 0;
    }

    public static int[] locatePlacePieceCoordinate(int column, int[][] curBoard, boolean showMessages) {
        int row = boardHeight - 1;

        if (!isInBounds(row, column)) { // if the row is not in bounds, return false early
            if (showMessages) afterDisplayBoardMessages.add("The column is out of bounds.");
            return new int[] {-1, -1};
        }

        for (row = boardHeight - 1; row >= 0; row--) {
            if (curBoard[row][column] == 0) {
                break;
            }
        }

        if (!isInBounds(row, column)) { // if the row is not in bounds, return false early
            if (showMessages) afterDisplayBoardMessages.add("The column is already full.");
            return new int[] {-1, -1};
        }

        return new int[] {row, column};
    }

    public static boolean placePiece(int column, int piece) {
        int[] coordinate = locatePlacePieceCoordinate(column, board, true);

        if (coordinate[0] == -1) {
            return false;
        }

        board[coordinate[0]][coordinate[1]] = piece;
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

    public static int[][] copyBoard(int[][] boardToCopy) {
        int[][] newBoard = new int[boardHeight][boardWidth];

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                newBoard[i][j] = boardToCopy[i][j];
            }
        }

        return newBoard;
    }

    public static int userAgent() {
        return sc.nextInt(); // validation is streamlined further down
    }

    public static int randomAgent() {
        return (int)(Math.random() * (boardWidth - 1 + 1) + 1);
    }

    public static int perfectAgent(int playerToMove) {
        int[][] possibleBoard;
        int bestMove = -1;

        // play the winning move if there is one
        for (int column = 0; column < boardWidth; column++) {
            possibleBoard = copyBoard(board);

            int[] possibleCoordinate = locatePlacePieceCoordinate(column, possibleBoard, false);
            if (possibleCoordinate[0] != -1){
                possibleBoard[possibleCoordinate[0]][possibleCoordinate[1]] = playerToMove;
                if (checkForWinCondition(possibleBoard) != 0) {
                    bestMove = column + 1;
                }
            }
        }

        // block potential opponent winning moves (three-in-a-row)
        int nextPlayerToMove = playerToMove % 2 + 1;

        for (int column = 0; column < boardWidth; column++) { // simulate opponent dropping pieces
            possibleBoard = copyBoard(board);

            // play the winning move as the opponent if there is one
            int[] opponentPossibleCoordinate = locatePlacePieceCoordinate(column, possibleBoard, false);
            if (opponentPossibleCoordinate[0] != -1){
                possibleBoard[opponentPossibleCoordinate[0]][opponentPossibleCoordinate[1]] = nextPlayerToMove;
                if (checkForWinCondition(possibleBoard) != 0) {
                    bestMove = column + 1;
                }
            }
        }


        if (bestMove > 0) {
            return bestMove;
        }

        // generate a valid random move
        while (true) {
            possibleBoard = copyBoard(board);

            int randomMove = randomAgent();

            int[] possibleCoordinate = locatePlacePieceCoordinate(randomMove - 1, possibleBoard, false);
            if (possibleCoordinate[0] != -1){
                bestMove = randomMove;
                return bestMove;
            }
        }
    }

    public static void game(int[] agents) {
        boolean hasQuit = false;
        int[] score = {0, 0};

        while (!hasQuit) {
            resetBoard();

            int curPlayer = 1;

            boolean hasEnded = false;

            while (!hasEnded) {
                // user turn
                System.out.println("Player " + playerCharacters[curPlayer] + " to move (Enter the column number): ");

                int curPlayerColumnSelection = switch (agents[curPlayer - 1]) {
                    case 1 -> randomAgent();
                    case 2 -> perfectAgent(curPlayer);
                    default -> userAgent();
                };

                if (placePiece(curPlayerColumnSelection - 1, curPlayer)) {
                    int winningPlayer = checkForWinCondition(board);

                    if (winningPlayer != 0) {
                        score[winningPlayer - 1] += 1;
                        globalScore[winningPlayer - 1] += 1;
                        displayWinMessage(winningPlayer, score);

                        hasEnded = true;
                    } else if (isFull(board)) {
                        displayWinMessage(0, score);
                        hasEnded = true;
                    }

                    curPlayer = curPlayer % 2 + 1;
                } else if (isFull(board)) {
                    displayWinMessage(0, score);
                    hasEnded = true;
                } else
                {
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
        game(new int[] {0, 2});
    }
}


