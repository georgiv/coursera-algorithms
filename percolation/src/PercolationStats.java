import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.lang.Integer;
import java.lang.Math;

public class PercolationStats {
  private double[] ts;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    ts = new double[trials];
    init(n, trials);
  }

  private void init(int n, int trials) {
    for (int i = 0; i < trials; i++) {
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
      }
      ts[i] = p.numberOfOpenSites() / (n * n);
    }
  }

  public double mean() {
    return StdStats.mean(ts);
  }

  public double stddev() {
    return StdStats.stddev(ts);
  }

  public double confidenceLo() {
    return mean() - (1.96 * stddev()) / Math.sqrt(ts.length);
  }

  public double confidenceHi() {
    return mean() + (1.96 * stddev()) / Math.sqrt(ts.length);
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);

    PercolationStats ps = new PercolationStats(n, T);

    System.out.println(ps.mean());
    System.out.println(ps.stddev());
    System.out.println(new double[] { ps.confidenceLo(), ps.confidenceHi() });
  }
}
