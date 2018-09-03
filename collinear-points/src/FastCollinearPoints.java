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
    if (points.length < 4) {
      return new LineSegment[0];
    }

    Point[] cp = new Point[points.length];
    for (int i = 0; i < points.length; i++) {
      cp[i] = points[i];
    }

    LineSegment[] res = new LineSegment[points.length];
    int resCount = 0;

    for (int i = 0; i < points.length; i++) {
      Arrays.sort(cp);
      Arrays.sort(cp, points[i].slopeOrder());

      int start = 1;
      int end = 1;

      while (start < cp.length) {
        while (end < cp.length) {
          if (points[i].slopeTo(cp[end]) == points[i].slopeTo(cp[start])) {
            end++;
          } else {
            break;
          }
        }

        if (end - start >= 3) {
          Point startP = cp[start].compareTo(points[i]) < 0 ? cp[start] : points[i];
          Point endP = cp[end - 1].compareTo(points[i]) > 0 ? cp[end - 1] : points[i];

          if (points[i] == startP) {
            if (resCount == res.length) {
              LineSegment[] newRes = new LineSegment[res.length * 2];
              for (int j = 0; j < res.length; j++) {
                newRes[j] = res[j];
              }
              res = newRes;
            }

            res[resCount] = new LineSegment(startP, endP);
            resCount++;
          }
        }
        start = end;
      }
    }

    LineSegment[] newRes = new LineSegment[resCount];
    for (int i = 0; i < resCount; i++) {
      newRes[i] = res[i];
    }
    res = newRes;

    return res;
  }
}
