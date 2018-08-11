import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
  private Point[] points;
  private Point[] lowers;
  private Point[] uppers;
  private int counter;
  private LineSegment[] ss;

  public FastCollinearPoints(Point[] points) {
    this.points = validate(points);
    this.lowers = new Point[points.length];
    this.uppers = new Point[points.length];
    this.counter = 0;
  }

  public int numberOfSegments() {
    if (ss == null) {
      ss = findSegments();
    }
    return ss.length;
  }

  public LineSegment[] segments() {
    if (ss == null) {
      ss = findSegments();
    }

    LineSegment[] res = new LineSegment[ss.length];
    for (int i = 0; i < ss.length; i++) {
      res[i] = ss[i];
    }
    return res;
  }

  private Point[] validate(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException();
      }
    }

    Point[] res = new Point[points.length];
    for (int i = 0; i < points.length; i++) {
      res[i] = points[i];
    }

    Arrays.sort(res);
    for (int i = 0; i < res.length - 1; i++) {
      if (res[i].compareTo(res[i + 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }

    return res;
  }

  private LineSegment[] findSegments() {
    LineSegment[] res = new LineSegment[0];
    if (points.length < 4) {
      return res;
    }

    for (int i = 0; i < points.length; i++) {
      Point[] others = new Point[points.length - 1];
      double[] slopes = new double[points.length - 1];
      for (int j = 0; j < points.length; j++) {
        if (i == j) {
          continue;
        }
        if (j < i) {
          others[j] = points[j];
          slopes[j] = points[i].slopeTo(points[j]);
        } else {
          others[j - 1] = points[j];
          slopes[j - 1] = points[i].slopeTo(points[j]);
        }
      }

      Arrays.sort(others, points[i].slopeOrder());
      Arrays.sort(slopes);

      int maxInt = 1;
      int maxIni = 0;
      int currentInt = 1;
      int currentIni = 0;
      for (int j = 1; j < others.length; j++) {
        if (slopes[j] == slopes[currentIni]) {
          currentInt++;
        } else {
          if (maxInt >= 3) {
            addSegment(points[i], others, maxIni, maxInt);
          }
          currentInt = 1;
          currentIni = j;
          maxInt = 1;
          maxIni = j;
        }

        if (currentInt > maxInt) {
          maxInt = currentInt;
          maxIni = currentIni;
        }
      }
      if (maxInt >= 3) {
        addSegment(points[i], others, maxIni, maxInt);
      }
    }

    LineSegment[] newRes = new LineSegment[counter];
    for (int i = 0; i < newRes.length; i++) {
      newRes[i] = new LineSegment(lowers[i], uppers[i]);
    }
    res = newRes;

    return res;
  }

  private void addSegment(Point origin, Point[] others, int initial, int interval) {
    Point lower = origin.compareTo(others[initial]) < 0 ? origin : others[initial];
    Point upper = origin.compareTo(others[initial + interval - 1]) > 0 ? origin : others[initial + interval - 1];

    boolean existing = false;
    for (int k = 0; k < counter; k++) {
      existing = lowers[k].compareTo(lower) == 0 && uppers[k].compareTo(upper) == 0;
      if (existing) {
        break;
      }
    }
    if (!existing) {
      if (counter == lowers.length) {
        Point[] newLowers = new Point[lowers.length * 2];
        for (int i = 0; i < lowers.length; i++) {
          newLowers[i] = lowers[i];
        }
        lowers = newLowers;

        Point[] newUppers = new Point[uppers.length * 2];
        for (int i = 0; i < uppers.length; i++) {
          newUppers[i] = uppers[i];
        }
        uppers = newUppers;
      }

      lowers[counter] = lower;
      uppers[counter] = upper;
      counter++;
    }
  }

  public static void main(String[] args) {
    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
    System.out.println(collinear.numberOfSegments());
  }
}
