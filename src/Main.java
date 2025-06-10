import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static String s = "";

    public static void displayMessageBox(String message) {
        // Clear the screen
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }

        System.out.println(message);
    }

    public static void displayMainMenu() {
        s = "213ew";
        System.out.println(s);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        displayMainMenu();
        displayMessageBox("hawk");
    }
    // hello ms mileti
}


