import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {

  private final int[][] blocks;
  private final int hamming;
  private final int manhattan;
  private final int emptyRow;
  private final int emptyCol;

  public Board(int[][] blocks) {
    int n = blocks.length;
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
    return blocks.length;
  }

  public int hamming() {
    return hamming;
  }

  private int expectedValue(int row, int col) {
    return row * blocks.length + col + 1;
  }

  public int manhattan() {
    return manhattan;
  }

  private int manhattanDistance(int row, int col, int value) {
    return Math.abs(row - expectedRow(value)) + Math.abs(col - expectedCol(value));
  }

  private int expectedRow(int value) {
    return (value - 1) / blocks.length;
  }

  private int expectedCol(int value) {
    return (value - 1) % blocks.length;
  }

  public boolean isGoal() {
    return hamming == 0;
  }

  public Board twin() {
    int[][] twinBlocks = new int[blocks.length][blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
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
    return StdRandom.uniform(blocks.length);
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
    if (this.blocks.length != that.blocks.length) {
      return false;
    }
    for (int i = 0; i < this.blocks.length; i++) {
      for (int j = 0; j < this.blocks.length; j++) {
        if (this.blocks[i][j] != that.blocks[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  public Iterable<Board> neighbors() {
    Stack<Board> stack = new Stack<>();
    int[][] blocksCopy = new int[blocks.length][blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
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
    if (emptyRow + 1 < blocks.length) {
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
    if (emptyCol + 1 < blocks.length) {
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
    StringBuilder builder = new StringBuilder(blocks.length + "\n");
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        builder.append(String.format("%2d ", blocks[i][j]));
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
