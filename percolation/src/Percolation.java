import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final WeightedQuickUnionUF grid;
  private final WeightedQuickUnionUF vGrid;
  private final int width;

  private boolean[] openSites;
  private int openSitesCount;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }

    grid = new WeightedQuickUnionUF(n * n + 1);
    vGrid = new WeightedQuickUnionUF(n * n + 2);
    openSites = new boolean[n * n];
    width = n;
  }

  private void validate(int row, int col) {
    if (row <= 0 || row > width || col <= 0 || col > width) {
      throw new IllegalArgumentException();
    }
  }

  public void open(int row, int col) {
    validate(row, col);

    if (!isOpen(row, col)) {
      openSites[(row - 1) * width - 1 + col] = true;
      openSitesCount++;
      if (row == 1) {
        grid.union(col - 1, width * width);
        vGrid.union(col - 1, width * width);
      }
      if (row == width) {
        vGrid.union((row - 1) * width - 1 + col, width * width + 1);
      }

      if ((row - 1) > 0 && isOpen(row - 1, col)) {
        grid.union((row - 1) * width - 1 + col, (row - 2) * width - 1 + col);
        vGrid.union((row - 1) * width - 1 + col, (row - 2) * width - 1 + col);
      }
      if ((row + 1) <= width && isOpen(row + 1, col)) {
        grid.union((row - 1) * width - 1 + col, row * width - 1 + col);
        vGrid.union((row - 1) * width - 1 + col, row * width - 1 + col);
      }
      if ((col - 1) > 0 && isOpen(row, col - 1)) {
        grid.union((row - 1) * width - 1 + col, (row - 1) * width - 1 + col - 1);
        vGrid.union((row - 1) * width - 1 + col, (row - 1) * width - 1 + col - 1);
      }
      if ((col + 1) <= width && isOpen(row, col + 1)) {
        grid.union((row - 1) * width - 1 + col, (row - 1) * width - 1 + col + 1);
        vGrid.union((row - 1) * width - 1 + col, (row - 1) * width - 1 + col + 1);
      }
    }
  }

  public boolean isOpen(int row, int col) {
    validate(row, col);

    return openSites[(row - 1) * width - 1 + col];
  }

  public boolean isFull(int row, int col) {
    validate(row, col);

    return grid.connected(width * width, (row - 1) * width - 1 + col);
  }

  public int numberOfOpenSites() {
    return openSitesCount;
  }

  public boolean percolates() {
    return vGrid.connected(width * width, width * width + 1);
  }
}
