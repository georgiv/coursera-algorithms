import java.util.Arrays;

public class BruteCollinearPoints {
  private Point[] points;
  private LineSegment[] ss;

  public BruteCollinearPoints(Point[] points) {
    this.points = validate(points);
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

    for (int i = 0; i < points.length - 3; i++) {
      for (int j = i + 1; j < points.length - 2; j++) {
        for (int k = j + 1; k < points.length - 1; k++) {
          for (int l = k + 1; l < points.length; l++) {
            Point[] c = new Point[]{ points[i], points[j], points[k], points[l]};
            if (isCollinear(c)) {
              LineSegment[] newRes = new LineSegment[res.length + 1];
              for (int m = 0; m < res.length; m++) {
                newRes[m] = res[m];
              }
              newRes[newRes.length - 1] = new LineSegment(c[0], c[c.length - 1]);
              res = newRes;
            }
          }
        }
      }
    }
    return res;
  }

  private boolean isCollinear(Point[] ps) {
    for (int i = 0; i < ps.length; i++) {
      Point[] others = new Point[3];
      int counter = 0;
      for (int j = 0; j < ps.length; j++) {
        if (i == j) {
          continue;
        }
        others[counter] = ps[j];
        counter++;
      }
      double slopeA = ps[i].slopeTo(others[0]);
      double slopeB = ps[i].slopeTo(others[1]);
      if (slopeA == slopeB) {
        double slopeC = ps[i].slopeTo(others[2]);
        if (slopeA == slopeC) {
          return true;
        }
      }
    }
    return false;
  }
}
