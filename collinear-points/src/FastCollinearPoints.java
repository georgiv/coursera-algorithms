import java.util.ArrayList;
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

    Point[] cp = new Point[points.length];
    for (int i = 0; i < points.length; i++) {
      cp[i] = points[i];
    }

    for (int i = 0; i < points.length; i++) {
      Arrays.sort(cp);
      Arrays.sort(cp, points[i].slopeOrder());

      int start = 1;
      int end = 1;

      while (start < cp.length) {
        while (end < cp.length && points[i].slopeTo(cp[end]) == points[i].slopeTo(cp[start])) end++;

        if (end - start >= 3) {
          Point startP = cp[start].compareTo(points[i]) < 0 ? cp[start] : points[i];
          Point endP = cp[end - 1].compareTo(points[i]) > 0 ? cp[end - 1] : points[i];

          if (points[i] == startP) {
            LineSegment[] newRes = new LineSegment[res.length + 1];
            for (int j = 0; j < res.length; j++) {
              newRes[j] = res[j];
            }
            newRes[newRes.length - 1] = new LineSegment(startP, endP);
            res = newRes;
          }
        }
        start = end;
      }
    }

    return res;
  }
}
