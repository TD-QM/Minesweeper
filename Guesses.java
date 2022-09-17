import java.util.ArrayList;

public class Guesses {
  ArrayList<Integer> x = new ArrayList<Integer>();
  ArrayList<Integer> y = new ArrayList<Integer>();

  public Guesses() {

  }

  public boolean checkGuess(int rowGuess, int colGuess) {
    for (int i = 0; i < x.size(); i++) {
      if ((x.get(i) == rowGuess) && (y.get(i) == colGuess)) {
        return false;
      }
    }
    x.add(rowGuess);
    y.add(colGuess);
    return true;
  }

  public boolean checkWin(int numGuess, int rows, int cols, int mines) {
    if ((rows * cols) - mines <= numGuess) {
      return true;
    }
    return false;
  }
}
