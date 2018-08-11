import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
  private Point[] points;
  private LineSegment[] ss;

  public FastCollinearPoints(Point[] points) { this.points = validate(points); }

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

    Point[] lowers = new Point[0];
    Point[] uppers = new Point[0];

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
          currentInt = 1;
          currentIni = j;
          if (maxInt >= 3) {
            Point lower = points[i].compareTo(others[maxIni]) < 0 ? points[i] : others[maxIni];
            Point upper = points[i].compareTo(others[maxIni + maxInt - 1]) > 0 ? points[i] : others[maxIni + maxInt - 1];

            boolean existing = false;
            for (int k = 0; k < lowers.length; k++) {
              existing = lowers[k].compareTo(lower) == 0 && uppers[k].compareTo(upper) == 0;
              if (existing) {
                break;
              }
            }
            if (!existing) {
              Point[] newLowers = new Point[lowers.length + 1];
              for (int k = 0; k < lowers.length; k++) {
                newLowers[k] = lowers[k];
              }
              newLowers[newLowers.length - 1] = lower;
              lowers = newLowers;

              Point[] newUppers = new Point[uppers.length + 1];
              for (int k = 0; k < uppers.length; k++) {
                newUppers[k] = uppers[k];
              }
              newUppers[newUppers.length - 1] = upper;
              uppers = newUppers;
            }
          }
          maxInt = 1;
          maxIni = j;
        }

        if (currentInt > maxInt) {
          maxInt = currentInt;
          maxIni = currentIni;
        }
      }

      if (maxInt >= 3) {
        Point lower = points[i].compareTo(others[maxIni]) < 0 ? points[i] : others[maxIni];
        Point upper = points[i].compareTo(others[maxIni + maxInt - 1]) > 0 ? points[i] : others[maxIni + maxInt - 1];

        boolean existing = false;
        for (int j = 0; j < lowers.length; j++) {
          existing = lowers[j].compareTo(lower) == 0 && uppers[j].compareTo(upper) == 0;
          if (existing) {
            break;
          }
        }
        if (!existing) {
          Point[] newLowers = new Point[lowers.length + 1];
          for (int j = 0; j < lowers.length; j++) {
            newLowers[j] = lowers[j];
          }
          newLowers[newLowers.length - 1] = lower;
          lowers = newLowers;

          Point[] newUppers = new Point[uppers.length + 1];
          for (int j = 0; j < uppers.length; j++) {
            newUppers[j] = uppers[j];
          }
          newUppers[newUppers.length - 1] = upper;
          uppers = newUppers;
        }
      }
    }

    LineSegment[] newRes = new LineSegment[lowers.length];
    for (int i = 0; i < newRes.length; i++) {
      newRes[i] = new LineSegment(lowers[i], uppers[i]);
    }
    res = newRes;

    return res;
  }
}
