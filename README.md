# Connect Four

A Connect Four implementation in Java with three different game modes.

# General logic

### Structure
- Separated into three types of functions: UI/display level, game logic, and player input ("agents")

### Entry (game() is discussed later)
**void main(String[] args)**
- A while loop is created which will break if **isRunning** becomes false
- The global score is displayed
- The user goes through the menu and can choose between Player vs. Player (1), Player vs. Random AI (2), Player vs. Perfect AI (3), or to quit the program (0).
- When any of the gamemodes are chosen (1-3), the **game()** function is launched with the two agents' ID.
  - Player vs. Player - agent 0 (user input) and agent 0 (user input)
  - Player vs. Random AI - agent 0 (user input) and agent 1 (random AI)
  - Player vs. Perfect AI - agent 0 (user input) and agent 2 (perfect AI)
- If an invalid gamemode is chosen (not 0-3), the default case of the switch case statement is reached. It will print an error message and do nothing else, allowing the loop to re-run and the options to be displayed again.

# Key functions and variables

## Logical functions (main game loop and agents)

### Agents
- Agents return a move based on different conditions (ex. user input (id of 0), random (id of 1), perfect algorithm (id of 2)) and is how **game()** receives input from each player.

**int userAgent()** - prompts the user for their chosen column and returns it. Input validation not nessecary here since it is handled in **game()**


**int randomAgent()** - keeps generating a random number until it finds a valid column (one that is not filled) and returns it


**int perfectAgent(int playerToMove)**
- Considers the best move based on the given **playerToMove** (either 1 or 2)
- Creates a copy of the board and simulates dropping pieces on it (resetting it after each simulation) as the opposing piece. If it is able to block a winning move, it saves the column as **bestMove**
- Resets the copy of the board, then simulates dropping **playerToMove**'s pieces on it. If a winning state is achieved after a move, it is saved and overrides **bestMove** (because it is better to win instantly than block a win)
- If neither of these cases results in a best move, a random move with **randomAgent()** is chosen instead. 

### Game loop
**void game(int agentOne, int agentTwo)**
- The two agents' ID are put into an array called **agents**. Player 1's agent is in index 0 while player 2's agent is in index 1.
- Creates a while loop which is broken when **hasQuit** becomes true
- Reset the board and set the **curPlayer** to 1
- Display the board initially to show an empty board state
- Create a while loop which will break when **hasEnded** becomes true
- Check if the board ends in a tie, immediately display the board and break the loop if so (this is to prevent duplicate tie messages)
- Print the board and the current player character using **playerCharacters[curPlayer]**
- Use the current player to get move of the current player using **agents** array.
- Place the piece.
  - If successful, check for win conditions and break if one is found by setting **hasEnded** to true. Increment and print the global and local scores. Change **curPlayer** to the next player.
  - If unsuccessful, add a general error message to the **afterDisplayBoardMessages** and display the board. Do not alter **curPlayer** since their turn was not successful and they should try again until it is. Since agents that the user doesn't control are always sanitized and won't return an invalid value, this won't disrupt the user experience, as they will never trigger this condition.
- After the while loop, once it has been broken (a win/tie condition has been met), prompt the user if they want to play another game (N) or return to the menu (Y). The repeated checking is handled in **promptPlayAgain().**
  - If true (the user wants to play again), do nothing, and the initial while loop will recreate the game again (resetting everything except for the score)
  - If false, break the first while loop by setting **hasQuit** to true and print a goodbye message. 

## Board functions

**int[][] board = new int[6][7];** - A 2D integer array representing the board.
- **boardHeight** - stores the height of the board for quick use
- **boardWidth** - stores the width of the board for quick use


**String[] playerCharacters = {" ", "X", "O"}** - An array of characters using the index to convert integers to user-friendly characters


**int[] globalScore = {0, 0}** - An array representing the total score for both players based on the index.


**boolean isInBounds(int row, int column)**
- returns whether or not the row and column is a valid index for a cell within the bounds of the array by checking it against **boardHeight** and **boardWidth** (row is greater than or equal to zero but less than **boardHeight**, column is greater than or equal to zero but less than **boardWidth**)

**boolean isFull(int[][] curBoard)**
- Returns whether or not the inputted board is full (no cell has 0) by iterating over every cell and returning false if it encounters a zero at any point.

**int[] locatePlacePieceCoordinate(int column, int[][] curBoard, boolean showMessages)**
- Simulates the dropping of a piece into **curBoard** by starting from the last row of the board and continuing to go up until an empty cell is found.
- The cell row and column are returned, or, if the column is out of bounds or full, {-1}.

**boolean placePiece(int column, int piece)**
- Uses **locatePlacePieceCoordinate()** to find and place a piece on the bottom of the grid.
- Returns whether or not the placing succeeded

**int checkForWinCondition(int[][] curBoard)**
- Iterates over every cell in **curBoard** in the 2D array using a nested for loop, and checks 3 units to the right, left, diagonally-down-right, and diagonally-down-left if doing so won't make the program go out of bounds.
  - It accesses cells in a row by adding 1, 2, and 3 to the row or column index of the initial cell. 
  - Skips doing this check if the initial cell is empty, since there would not be a match no matter what (there can at most be three units in a row if this is the case) 
- Returns the winner if there is one, otherwise returns 0.

## Display functions

**void displayBoard()**
- Displays the main board array by iterating through each element and printing it.
- Also prints all the **afterDisplayBoardMessages** after printing the board with a for-each loop.

**void displayWinMessage(int winner, int[] score)**
- winner - stores who won the game (0 for tie, 1 for P1, 2 for P2 / AI)
- score - displays the score 
- Adds the win message to **afterDisplayBoardMessages** to be printed after the final board

**void promptPlayAgain()**
- Prompts the user if they want to play again using a while loop, only breaking once a valid choice is reached.
