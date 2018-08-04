import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double CONFIDENCE_95 = 1.96;

  private double[] ts;
  private double m;
  private double s;

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
      ts[i] = (double) p.numberOfOpenSites() / (n * n);
    }
  }

  public double mean() {
    if (m == 0.0) {
      m = StdStats.mean(ts);
    }
    return m;
  }

  public double stddev() {
    if (s == 0.0) {
      s = StdStats.stddev(ts);
    }
    return s;
  }

  public double confidenceLo() {
    return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(ts.length);
  }

  public double confidenceHi() {
    return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(ts.length);
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);

    PercolationStats ps = new PercolationStats(n, t);

    double m = ps.mean();
    double s = ps.stddev();
    double[] c = new double[] { ps.confidenceLo(), ps.confidenceHi() };
    System.out.println(m);
    System.out.println(s);
    System.out.println(c[0]);
    System.out.println(c[1]);
  }
}
