import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public final class PercolationStats {

  private final int trials;
  private final double[] thresholds;

  public PercolationStats(int n, int trials) {
    if (n < 1) {
      throw new IllegalArgumentException("n < 1.");
    }
    if (trials < 1) {
      throw new IllegalArgumentException("trials < 1.");
    }
    this.trials = trials;
    this.thresholds = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);
        p.open(row, col);
      }
      thresholds[i] = (double) p.numberOfOpenSites() / (n * n);
    }
  }

  public double mean() {
    return StdStats.mean(thresholds);
  }

  public double stddev() {
    return StdStats.stddev(thresholds);
  }

  public double confidenceLo() {
    return mean() - 1.96 * stddev() / Math.sqrt(trials);
  }

  public double confidenceHi() {
    return mean() + 1.96 * stddev() / Math.sqrt(trials);
  }

  public static void main(String[] args) {
    int n = StdIn.readInt();
    int trials = StdIn.readInt();
    PercolationStats stats = new PercolationStats(n, trials);
    StdOut.println("mean = " + stats.mean());
    StdOut.println("stddev = " + stats.stddev());
    double confidenceLo = stats.confidenceLo();
    double confidenceHi = stats.confidenceHi();
    StdOut.println("95% confidence interval = [" + confidenceLo + ", " + confidenceHi + "]");
  }
}
