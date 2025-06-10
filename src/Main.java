import javax.swing.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static Integer[][] board = new Integer[6][7];

    public static void displayMessageBox(String message) {

    }

    public static void displayMainMenu() {

    }

    public static void displayBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[0].length; j++) {
                switch (board[i][j]) {
                    case 0: // empty
                        System.out.print("  |");
                        break;
                    case 1: // X
                        System.out.print(" X |");
                        break;
                    case 2: // O
                        System.out.print(" O |");
                        break;
                    default: // error
                        System.out.print(" ? |");
                        break;
                }
            }
            System.out.println();
        }
    }

    public static void initializeBoard() {
        // initialize board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
    }

    public static void checkForMatches() {
        // check horizontally
        // check vertically
        // check diagonally
    }

    public static void PVP() {
        boolean p1Won = false;
        boolean p2Won = false;


    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        initializeBoard();
        displayBoard();
    }
}


