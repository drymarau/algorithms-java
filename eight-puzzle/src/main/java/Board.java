import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {

  private final int[][] blocks;
  private final int n;
  private final int hamming;
  private final int manhattan;
  private final int emptyRow;
  private final int emptyCol;

  public Board(int[][] blocks) {
    this.n = blocks.length;
    this.blocks = new int[n][n];
    int count = 0;
    int sum = 0;
    int row = 0;
    int col = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int value = blocks[i][j];
        this.blocks[i][j] = value;
        if (value == 0) {
          row = i;
          col = j;
          continue;
        }
        if (value != expectedValue(i, j)) {
          count++;
          sum += manhattanDistance(i, j, value);
        }
      }
    }
    this.emptyRow = row;
    this.emptyCol = col;
    this.hamming = count;
    this.manhattan = sum;
  }

  public int dimension() {
    return n;
  }

  public int hamming() {
    return hamming;
  }

  private int expectedValue(int row, int col) {
    return row * n + col + 1;
  }

  public int manhattan() {
    return manhattan;
  }

  private int manhattanDistance(int row, int col, int value) {
    return Math.abs(row - expectedRow(value)) + Math.abs(col - expectedCol(value));
  }

  private int expectedRow(int value) {
    return (value - 1) / n;
  }

  private int expectedCol(int value) {
    return (value - 1) % n;
  }

  public boolean isGoal() {
    return hamming == 0;
  }

  public Board twin() {
    int[][] twinBlocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        twinBlocks[i][j] = blocks[i][j];
      }
    }
    int i1 = randomIndex();
    int j1 = randomIndex();
    while (twinBlocks[i1][j1] == 0) {
      i1 = randomIndex();
      j1 = randomIndex();
    }
    int i2 = randomIndex();
    int j2 = randomIndex();
    while (twinBlocks[i2][j2] == 0 || (i2 == i1 && j2 == j1)) {
      i2 = randomIndex();
      j2 = randomIndex();
    }
    int temp = twinBlocks[i1][j1];
    twinBlocks[i1][j1] = twinBlocks[i2][j2];
    twinBlocks[i2][j2] = temp;
    return new Board(twinBlocks);
  }

  private int randomIndex() {
    return StdRandom.uniform(n);
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (this.getClass() != o.getClass()) {
      return false;
    }
    Board that = (Board) o;
    if (this.n != that.n) {
      return false;
    }
    for (int i = 0; i < this.n; i++) {
      for (int j = 0; j < this.n; j++) {
        if (this.blocks[i][j] != that.blocks[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  public Iterable<Board> neighbors() {
    Stack<Board> stack = new Stack<>();
    int[][] blocksCopy = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocksCopy[i][j] = blocks[i][j];
      }
    }
    int temp;
    if (emptyRow - 1 >= 0) {
      temp = blocksCopy[emptyRow - 1][emptyCol];
      blocksCopy[emptyRow - 1][emptyCol] = 0;
      blocksCopy[emptyRow][emptyCol] = temp;
      stack.push(new Board(blocksCopy));
      temp = blocksCopy[emptyRow][emptyCol];
      blocksCopy[emptyRow][emptyCol] = 0;
      blocksCopy[emptyRow - 1][emptyCol] = temp;
    }
    if (emptyRow + 1 < n) {
      temp = blocksCopy[emptyRow + 1][emptyCol];
      blocksCopy[emptyRow + 1][emptyCol] = 0;
      blocksCopy[emptyRow][emptyCol] = temp;
      stack.push(new Board(blocksCopy));
      temp = blocksCopy[emptyRow][emptyCol];
      blocksCopy[emptyRow][emptyCol] = 0;
      blocksCopy[emptyRow + 1][emptyCol] = temp;
    }
    if (emptyCol - 1 >= 0) {
      temp = blocksCopy[emptyRow][emptyCol - 1];
      blocksCopy[emptyRow][emptyCol - 1] = 0;
      blocksCopy[emptyRow][emptyCol] = temp;
      stack.push(new Board(blocksCopy));
      temp = blocksCopy[emptyRow][emptyCol];
      blocksCopy[emptyRow][emptyCol] = 0;
      blocksCopy[emptyRow][emptyCol - 1] = temp;
    }
    if (emptyCol + 1 < n) {
      temp = blocksCopy[emptyRow][emptyCol + 1];
      blocksCopy[emptyRow][emptyCol + 1] = 0;
      blocksCopy[emptyRow][emptyCol] = temp;
      stack.push(new Board(blocksCopy));
      temp = blocksCopy[emptyRow][emptyCol];
      blocksCopy[emptyRow][emptyCol] = 0;
      blocksCopy[emptyRow][emptyCol + 1] = temp;
    }
    return stack;
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        builder.append(String.format("%2d ", blocks[i][j]));
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
