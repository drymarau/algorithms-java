import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public final class Percolation {

  private final int n;
  private final WeightedQuickUnionUF uf;
  private final boolean[] openSites;

  private int openSitesCount;

  public Percolation(int n) {
    if (n < 1) {
      throw new IllegalArgumentException("n < 1.");
    }
    this.n = n;
    this.uf = new WeightedQuickUnionUF(n * n + 2);
    this.openSites = new boolean[n * n + 2];
    for (int i = 1; i <= n; i++) {
      uf.union(i, 0);
      uf.union(rowColTo1D(n, i), n * n + 1);
    }
  }

  public void open(int row, int col) {
    checkBounds(row);
    checkBounds(col);
    if (isOpenInternal(row, col)) {
      return;
    }
    int rowCol1D = rowColTo1D(row, col);
    openSites[rowCol1D] = true;
    openSitesCount++;
    int upperRow = row - 1;
    int lowerRow = row + 1;
    int leftCol = col - 1;
    int rightCol = col + 1;
    if (upperRow > 0) {
      if (isOpenInternal(upperRow, col)) {
        uf.union(rowColTo1D(upperRow, col), rowCol1D);
      }
    }
    if (lowerRow <= n) {
      if (isOpenInternal(lowerRow, col)) {
        uf.union(rowColTo1D(lowerRow, col), rowCol1D);
      }
    }
    if (leftCol > 0) {
      if (isOpenInternal(row, leftCol)) {
        uf.union(rowColTo1D(row, leftCol), rowCol1D);
      }
    }
    if (rightCol <= n) {
      if (isOpenInternal(row, rightCol)) {
        uf.union(rowColTo1D(row, rightCol), rowCol1D);
      }
    }
  }

  public boolean isOpen(int row, int col) {
    checkBounds(row);
    checkBounds(col);
    return isOpenInternal(row, col);
  }

  private boolean isOpenInternal(int row, int col) {
    return openSites[rowColTo1D(row, col)];
  }

  public boolean isFull(int row, int col) {
    checkBounds(row);
    checkBounds(col);
    return isOpenInternal(row, col) && uf.connected(0, rowColTo1D(row, col));
  }

  public int numberOfOpenSites() {
    return openSitesCount;
  }

  public boolean percolates() {
    if (n == 1) {
      return isOpenInternal(n, n);
    }
    return uf.connected(0, n * n + 1);
  }

  private void checkBounds(int i) {
    if (i < 1 || i > n) {
      throw new IndexOutOfBoundsException("row or column index is out of bounds.");
    }
  }

  private int rowColTo1D(int row, int col) {
    return (row - 1) * n + col;
  }
}