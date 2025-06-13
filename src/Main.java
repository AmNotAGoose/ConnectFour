import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static int[][] board = new int[6][7]; // a 2D integer array representing the board.
    static int boardWidth = board[0].length;
    static int boardHeight = board.length;

    static String[] playerCharacters = {" ", "X", "O"}; // an array of characters using the index to convert integers to user-friendly characters

    static int[] globalScore = {0, 0}; // an array representing the total score player one and two has won based on the index.

    static ArrayList<String> afterDisplayBoardMessages = new ArrayList<>(); // an arraylist of messages to print after the board is printed in displayBoard(). this streamlines printing error messages and makes it more user-friendly (the user does not need to look back and forth to see what they did wrong).

    public static void displayBoard() {
        System.out.println("==============================");

        // display the top row of numbers
        for (int i = 1; i <= boardWidth; i++) {
            System.out.print("  " + i + " ");
        }

        System.out.println();

        // display the grid
        for (int[] row : board) {
            System.out.print("|");
            for (int j = 0; j < boardWidth; j++) {
                System.out.print(" " + playerCharacters[row[j]] + " |"); // use the playerCharacters array to convert the data to human-readable characters.
            }
            System.out.println();
        }

        // display any messages that need to be printed after the board
        for (String message : afterDisplayBoardMessages) {
            System.out.println(message);
        }

        // clear the messages
        afterDisplayBoardMessages = new ArrayList<>();
    }

    public static void displayWinMessage(int winner, int[] score) {
        // display the game win message.
        if (winner == 0) { // 0 means the game ends in a tie.
            afterDisplayBoardMessages.add("The game ended in a tie.");
        } else {
            afterDisplayBoardMessages.add("Player " + playerCharacters[winner] + " won the game.");
        }
        afterDisplayBoardMessages.add("Current game score: " + playerCharacters[1] + ": " + score[0] + " | " + playerCharacters[2] + ": " + score[1]);
        afterDisplayBoardMessages.add("Lifetime score: " + playerCharacters[1] + ": " + globalScore[0] + " | " + playerCharacters[2] + ": " + globalScore[1]);
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
                        if (cell == curBoard[row + 1][column + 1] && cell == curBoard[row + 2][column + 2] && cell == curBoard[row + 3][column + 3]) {
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
            return new int[] {-1}; // return -1 if there is an error
        }

        for (row = boardHeight - 1; row >= 0; row--) {
            if (curBoard[row][column] == 0) {
                break;
            }
        }

        if (!isInBounds(row, column)) { // if the row is not in bounds, return false early
            if (showMessages) afterDisplayBoardMessages.add("The column is already full.");
            return new int[] {-1}; // return -1 if there is an error
        }

        return new int[] {row, column};
    }

    public static boolean placePiece(int column, int piece) {
        // places the piece on the global board at a specific column
        int[] coordinate = locatePlacePieceCoordinate(column, board, true);

        if (coordinate[0] == -1) {
            return false;
        }

        board[coordinate[0]][coordinate[1]] = piece;
        return true;
    }

    public static boolean promptPlayAgain() {
        // prompts the user again until a valid choice is selected.
        sc.nextLine();

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
        // create a deep copy of the board.

        int[][] newBoard = new int[boardHeight][boardWidth];

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                newBoard[i][j] = boardToCopy[i][j];
            }
        }

        return newBoard;
    }

    // agents return a move

    public static int userAgent() {
        System.out.print("Enter the column number you wish to place your piece in: "); // prompt the user here
        return sc.nextInt(); // validation is streamlined further down, so it is fine
    }

    public static int randomAgent() {
        // generates a random number within the range

        int randomMove;

        // validation must be done here for non-user input agents since it would disrupt the user experience if streamlined further down (will print error messages the user did not cause)
        while (true) {
            randomMove = (int)(Math.random() * (boardWidth - 1 + 1) + 1);

            int[] possibleCoordinate = locatePlacePieceCoordinate(randomMove - 1, board, false);
            if (possibleCoordinate[0] != -1){
                break; // if the coordinate is valid, select it by breaking the random generator.
            }
        }

        return randomMove;
    }

    public static int perfectAgent(int playerToMove) {
        // this agent is still susceptible to forking, but it follows the 2 rules posted on the document.
        int[][] possibleBoard;
        int bestMove = -1;

        // block potential opponent winning moves (three-in-a-row)
        int nextPlayerToMove = playerToMove % 2 + 1;

        for (int column = 0; column < boardWidth; column++) { // simulate opponent dropping pieces
            possibleBoard = copyBoard(board); // reset testing board for simulation

            // play the winning move as the opponent if there is one
            int[] opponentPossibleCoordinate = locatePlacePieceCoordinate(column, possibleBoard, false);
            if (opponentPossibleCoordinate[0] != -1){
                possibleBoard[opponentPossibleCoordinate[0]][opponentPossibleCoordinate[1]] = nextPlayerToMove;
                if (checkForWinCondition(possibleBoard) != 0) {
                    bestMove = column + 1;
                }
            }
        }

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

        // if the best move is valid, return it
        if (bestMove > 0) {
            return bestMove;
        }

        // otherwise, generate a valid random move if there is no winning or blocking move to be made
        return randomAgent(); // validation of the random number is handled within randomAgent
    }

    public static void game(int agentOne, int agentTwo) {
        boolean hasQuit = false;
        int[] score = {0, 0};

        int[] agents = {agentOne, agentTwo};

        while (!hasQuit) {
            resetBoard(); // reset the board before every game.

            int curPlayer = 1;

            boolean hasEnded = false;

            displayBoard();

            while (!hasEnded) {
                if (isFull(board)) { // check if there is a draw
                    displayWinMessage(0, score);
                    displayBoard(); // display the board since the rest of the loop will not run.
                    break;
                }

                System.out.println("Player " + playerCharacters[curPlayer] + " to move.");

                int curPlayerColumnSelection = switch (agents[curPlayer - 1]) {
                    case 1 -> randomAgent();
                    case 2 -> perfectAgent(curPlayer);
                    default -> userAgent();
                };

                if (placePiece(curPlayerColumnSelection - 1, curPlayer)) { // place a piece if possible
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

                    curPlayer = curPlayer % 2 + 1; // select the next player
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
        boolean isRunning = true;

        while (isRunning){
            System.out.println("======= Connect 4 =======");

            System.out.println("Lifetime score: " + playerCharacters[1] + ": " + globalScore[0] + " | " + playerCharacters[2] + ": " + globalScore[1]);

            System.out.println("1 - Player vs. Player");
            System.out.println("2 - Player vs. Random AI");
            System.out.println("3 - Player vs. Perfect AI");
            System.out.println("0 - Exit the session");

            System.out.print("Enter an option: ");

            int gamemode = sc.nextInt();

            switch (gamemode) {
                case 1:
                    game(0, 0);
                    break;
                case 2:
                    game(0, 1);
                    break;
                case 3:
                    game(0, 2);
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("That is not a valid gamemode. Press enter to retry.");
            }
        }

        System.out.println("Thank you for playing Connect 4. Goodbye.");
    }
}
