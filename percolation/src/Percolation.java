import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF grid;
  private int[] openSites;
  private int openSitesCount;
  private int width;
  private boolean isPerculates;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }

    grid = new WeightedQuickUnionUF(n * n + 2);
    openSites = new int[n * n];
    width = n;
  }

  public void open(int row, int col) {
    validate(row, col);

    if (!isOpen(row, col)) {
      openSites[(row - 1) * width - 1 + col] = 1;
      openSitesCount++;
      if (row == 1) {
        grid.union(col - 1, width * width);
      }

      if ((row - 1) > 0 && isOpen(row - 1, col)) {
        grid.union((row - 1) * width - 1 + col, (row - 2) * width - 1 + col);
      }
      if ((row + 1) <= width && isOpen(row + 1, col)) {
        grid.union((row - 1) * width - 1 + col, row * width - 1 + col);
      }
      if ((col - 1) > 0 && isOpen(row, col - 1)) {
        grid.union((row - 1) * width - 1 + col, (row - 1) * width - 1 + col - 1);
      }
      if ((col + 1) <= width && isOpen(row, col + 1)) {
        grid.union((row - 1) * width - 1 + col, (row - 1) * width - 1 + col + 1);
      }
    }
  }

  public boolean isOpen(int row, int col) {
    validate(row, col);

    return openSites[(row - 1) * width - 1 + col] == 1;
  }

  public boolean isFull(int row, int col) {
    validate(row, col);

    boolean res =  grid.connected(width * width, (row - 1) * width - 1 + col);

    if (!isPerculates && row == width && res) {
      isPerculates = true;
    }

    return res;
  }

  public int numberOfOpenSites() {
    return openSitesCount;
  }

  public boolean percolates() {
    return isPerculates;
  }

  private void validate(int row, int col) {
    if (row <= 0 || row > width || col <= 0 || col > width) {
      throw new IllegalArgumentException();
    }
  }
}
