import java.util.*;

class Main {
  public static void main(String[] args) {
    // Making a Scanner for user input
    Scanner input = new Scanner(System.in);

    int row;
    int col;
    int mine;
    int rowGuess;
    int colGuess;
    int guesses = 0;
    Guesses x = new Guesses();

    // Asking the user for the parameters of the playing field
    // Number of rows
    System.out.print("How many rows? (min of 5): ");
    while (true) {
      row = input.nextInt();
      if (row >= 5) {
        break;
      } else {
        System.out.println("Invalid number, please try again");
      }
    }

    // Number of columns
    System.out.print("\nHow many columns? (min of 5): ");
    while (true) {
      col = input.nextInt();
      if (col >= 5) {
        break;
      } else {
        System.out.println("Invalid number, please try again");
      }
    }

    // Number of mines
    System.out.print("\nHow many mines?: ");
    while (true) {
      mine = input.nextInt();
      if (mine <= (row * col) - 9) {
        break;
      } else {
        System.out.println("Too many, please try again");
      }
    }

    // Making the playing field that the user actually sees
    String[][] playerGrid = makePlayerGrid(row, col);
    printGrid(playerGrid);
    System.out.println();

    // Player keeps inputting rows until they have one inside the parameters
    while (true) {
      System.out.print("What row would you like to guess?: ");
      rowGuess = input.nextInt();
      System.out.println();
      if (rowGuess >= 0 && rowGuess < row) {
        break;
      } else {
        System.out.println("Invalid row, please try again");
      }
    }
    while (true) {
      System.out.print("What column would you like to guess?: ");
      colGuess = input.nextInt();
      System.out.println();
      if (colGuess >= 0 && colGuess < col) {
        break;
      } else {
        System.out.println("Invalid column, please try again");
      }
    }

    // Making the playing field based on the above parameters
    // The first guess is also taken into account so the player doesn't immediately die
    int[][] grid = makeGrid(row, col, mine, rowGuess, colGuess);

    // Gameplay loop woooo
    while (true) {
      // Increment the number of correct guesses for the win state
      guesses++;

      // Update the player's playing grid
      playerGrid[rowGuess][colGuess] = "  " + String.valueOf(grid[rowGuess][colGuess]) + "  ";

      // Print out saud playing grid
      printGrid(playerGrid);

      // Lose state (if the player hits a 99 space aka a mine)
      if (grid[rowGuess][colGuess] == 99) {
        System.out.println("You hit a mine! You died.");
        break;
      }

      // Win state (if the player hits all the empty spaces)
      if ((row * col) - mine <= guesses) {
        System.out.println("You evaded all the mines! You win!");
        break;
      }

      // Player keeps inputting rows until they have one inside the parameters
      // This is after the checks due to the first guesses being outside the outer while loop
      // Also checks to see if the player already put in a value or not
      while (true) {
        while (true) {
          System.out.print("What row would you like to guess?: ");
          rowGuess = input.nextInt();
          System.out.println();
          if (rowGuess >= 0 && rowGuess < row) {
            break;
          } else {
            System.out.println("Invalid row, please try again");
          }
        }
        while (true) {
          System.out.print("What column would you like to guess?: ");
          colGuess = input.nextInt();
          System.out.println();
          if (colGuess >= 0 && colGuess < row) {
            break;
          } else {
            System.out.println("Invalid column, please try again");
          }
        }
        if (x.checkGuess(rowGuess, colGuess)) {
          break;
        } else {
          System.out.println("You already put that square!");
        }
      }

    }
    // Close scanner :)
    input.close();

  }

  // Function for making the playing grid
  // It shows all the mines and also the numbers next to the mines
  public static int[][] makeGrid(int row, int column, int mines, int firstRow, int firstCol) {
    int[][] grid = new int[row][column];

    // Algorithm for randomly assigning places as mines and then adding the values
    //   next to them
    for (int i = 0; i < mines; i++) {
      boolean x = true;
      while (x) {
        int randRow = (int) ((Math.random() * row));
        int randCol = (int) ((Math.random() * column));

        // If the square isn't already a mine, make it one
        // Also checks to see if the random row and column are too close to the starting 
        //   guess or not
        if ((grid[randRow][randCol] < 99) && (((randRow < firstRow - 1) || (randRow > firstRow + 1))
            || ((randCol < firstCol - 1) || (randCol > firstCol + 1)))) {
          grid[randRow][randCol] = 99;

          // For the corners and sides, in order to escape an array out of bounds exception, 
          //   they're under special cases where only specific squares are added to
          if (randRow == 0 && randCol == 0) { // Top left corner
            grid[randRow + 1][randCol] += 1;
            grid[randRow][randCol + 1] += 1;
            grid[randRow + 1][randCol + 1] += 1;
          } else if ((randRow == row - 1) && (randCol == column - 1)) { // Bottom right corner
            grid[randRow - 1][randCol] += 1;
            grid[randRow][randCol - 1] += 1;
            grid[randRow - 1][randCol - 1] += 1;
          } else if (randRow == 0 && (randCol == column - 1)) { // Top right corner
            grid[randRow][randCol - 1] += 1;
            grid[randRow + 1][randCol - 1] += 1;
            grid[randRow + 1][randCol] += 1;
          } else if ((randRow == row - 1) && randCol == 0) { // Bottom left corner
            grid[randRow - 1][randCol] += 1;
            grid[randRow - 1][randCol + 1] += 1;
            grid[randRow][randCol + 1] += 1;
          } else if (randRow == 0) { // First row
            grid[randRow][randCol - 1] += 1;
            grid[randRow][randCol + 1] += 1;
            grid[randRow + 1][randCol - 1] += 1;
            grid[randRow + 1][randCol] += 1;
            grid[randRow + 1][randCol + 1] += 1;
          } else if (randCol == 0) { // First column
            grid[randRow - 1][randCol] += 1;
            grid[randRow + 1][randCol] += 1;
            grid[randRow - 1][randCol + 1] += 1;
            grid[randRow][randCol + 1] += 1;
            grid[randRow + 1][randCol + 1] += 1;
          } else if (randRow == row - 1) { // Last row
            grid[randRow - 1][randCol - 1] += 1;
            grid[randRow - 1][randCol] += 1;
            grid[randRow - 1][randCol + 1] += 1;
            grid[randRow][randCol - 1] += 1;
            grid[randRow][randCol + 1] += 1;
          } else if (randCol == column - 1) { // Last column
            grid[randRow - 1][randCol - 1] += 1;
            grid[randRow][randCol - 1] += 1;
            grid[randRow + 1][randCol - 1] += 1;
            grid[randRow - 1][randCol] += 1;
            grid[randRow + 1][randCol] += 1;
          } else { // General case
            grid[randRow - 1][randCol - 1] += 1;
            grid[randRow - 1][randCol] += 1;
            grid[randRow - 1][randCol + 1] += 1;
            grid[randRow][randCol - 1] += 1;
            grid[randRow][randCol + 1] += 1;
            grid[randRow + 1][randCol - 1] += 1;
            grid[randRow + 1][randCol] += 1;
            grid[randRow + 1][randCol + 1] += 1;
          }
          x = false;
        }
      }
    }

    // Making the mines always 99 instead of 100, 101, and so on
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] > 99) {
          grid[i][j] = 99;
        }
      }
    }

    // Returns the grid, pretty self-explanatory
    return grid;
  }

  // Makes player's grid that they see
  public static String[][] makePlayerGrid(int row, int col) {
    String[][] grid = new String[row][col];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = "[" + i + "," + j + "]";
      }
    }
    return grid;
  }

  // This prints out the actual grid with the mines
  // Used for testing purposes
  public static void printGrid(int[][] grid1) {
    for (int i = 0; i < grid1.length; i++) {
      for (int j = 0; j < grid1[i].length; j++) {
        System.out.print(grid1[i][j] + "\t");
      }
      System.out.println();
    }
  }

  // Prints the player's grid
  public static void printGrid(String[][] grid1) {
    for (int i = 0; i < grid1.length; i++) {
      for (int j = 0; j < grid1[i].length; j++) {
        System.out.print(grid1[i][j] + "\t");
      }
      System.out.println();
    }
  }

}