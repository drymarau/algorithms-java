import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class FastCollinearPoints {

  private List<LineSegment> segments = new LinkedList<>();

  public FastCollinearPoints(Point[] points) {
    checkNotNull(points, "points == null.");
    Point[] copy = Arrays.copyOf(points, points.length);
    Arrays.sort(copy);
    checkNoRepeated(copy);
    for (int i = 0; i < points.length; i++) {
      int count = 1;
      Point p = points[i];
      Arrays.sort(copy, p.slopeOrder());
      for (int j = 1; j < copy.length; j++) {
        Point previousPoint = copy[j - 1];
        Point currentPoint = copy[j];
        double previousSlope = p.slopeTo(previousPoint);
        double currentSlope = p.slopeTo(currentPoint);
        if (Double.compare(previousSlope, currentSlope) == 0) {
          count++;
        } else {
          if (count > 2) {
            segments.add(new LineSegment(p, previousPoint));
          }
          count = 1;
        }
        if (j == copy.length - 1 && count > 2) {
          segments.add(new LineSegment(p, currentPoint));
        }
      }
    }
  }

  public int numberOfSegments() {
    return segments.size();
  }

  public LineSegment[] segments() {
    return segments.toArray(new LineSegment[0]);
  }

  private static <T> T checkNotNull(T object, String message) {
    if (object == null) {
      throw new NullPointerException(message);
    }
    return object;
  }

  private static Point[] checkNoRepeated(Point[] points) {
    for (int i = 1; i < points.length; i++) {
      if (points[i].compareTo(points[i - 1]) == 0) {
        throw new IllegalArgumentException("points has repeated point.");
      }
    }
    return points;
  }

  public static void main(String[] args) {
    Point[] points = new Point[] {
        new Point(10000, 0), new Point(0, 10000), new Point(3000, 7000), new Point(7000, 3000),
        new Point(20000, 21000), new Point(3000, 4000), new Point(14000, 15000),
        new Point(6000, 7000)
    };

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
  }
}