# Connect Four

# General logic
1. The user goes through the menu and can choose between Player vs. Player, Player vs. 
2. 

# Key functions and variables

## Board functions

**int[][] board = new int[6][7];** - A 2D integer array representing the board.
- **boardHeight** - stores the height of the board for quick use
- **boardWidth** - stores the width of the board for quick use
**String[] playerCharacters = {" ", "X", "O"}** - An array of characters using the index to convert integers to user-friendly characters
**int[] globalScore = {0, 0}** - An array representing the total score for both players based on the index.

**boolean isInBounds(int row, int column)**
- returns whether or not the row and column is a valid index for a cell within the bounds of the array by checking it against **boardHeight** and **boardWidth**

**boolean isFull(int[][] curBoard)**
- determines whether or not the inputted board is full (no cell has 0)

**int[] locatePlacePieceCoordinate(int column, int[][] curBoard, boolean showMessages)**
- simulates the dropping of a piece into **curBoard** by starting from the last row of the board and continuing to go up until an empty cell is found.
- the cell row and column are returned, or, if the column is out of bounds or full, {-1}.

**boolean placePiece(int column, int piece)**
- uses **locatePlacePieceCoordinate()** to find and place a piece on the bottom of the grid.
- returns whether or not the placing succeeded

**int checkForWinCondition(int[][] curBoard)**
- Iterates over every cell in **curBoard** in the 2D array using a nested for loop, and checks 3 units to the right, left, diagonally-down-right, and diagonally-down-left if doing so won't make the program go out of bounds.
- checks if the cell is empty, then it skips checking this cell to reduce useless checks
- returns the winner if there is one, otherwise returns 0.

## Menu functions

**void main(String[] args)**
- isRunning - keeps the while loop running until the user inputs 0
- The program keeps running until the user enters one of the menu options, then runs the **game** function based on which agents the user chose.

## Display functions

**void displayBoard()**
- Displays the main board array by iterating through each element and printing it.
- Also prints all the **afterDisplayBoardMessages** after printing the board with a for-each loop.

**void displayWinMessage(int winner, int[] score)**
- winner - stores who won the game (0 for tie, 1 for P1, 2 for P2 / AI)
- score - displays the score 
- Adds the win message to **afterDisplayBoardMessages** to be printed after the final board

**void promptPlayAgain()**
- Prompts the user for if they want to play again using a while loop, only breaking once a valid choice is reached.


