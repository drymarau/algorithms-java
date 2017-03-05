import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class BruteCollinearPoints {

  private List<LineSegment> segments = new LinkedList<>();

  public BruteCollinearPoints(Point[] points) {
    checkNotNull(points, "points == null.");
    Point[] copy = Arrays.copyOf(points, points.length);
    Arrays.sort(copy);
    checkNoRepeated(copy);
    int length = copy.length;
    for (int p = 0; p < length - 3; p++) {
      for (int q = p + 1; q < length - 2; q++) {
        for (int r = q + 1; r < length - 1; r++) {
          if (Double.compare(copy[p].slopeTo(copy[q]), copy[p].slopeTo(copy[r])) != 0) {
            continue;
          }
          for (int s = r + 1; s < length; s++) {
            if (Double.compare(copy[p].slopeTo(copy[s]), copy[p].slopeTo(copy[q])) != 0) {
              continue;
            }
            segments.add(new LineSegment(copy[p], copy[s]));
          }
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
}
