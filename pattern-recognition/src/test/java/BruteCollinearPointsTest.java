import org.junit.Test;

public final class BruteCollinearPointsTest {

  @Test(expected = NullPointerException.class) public void throwsNullPointerExceptionIfInputIsNull()
      throws Exception {
    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(null);
  }

  @Test(expected = NullPointerException.class)
  public void throwsNullPointerExceptionIfPointInInputIsNull() throws Exception {
    Point[] input = new Point[] { null, null, null };
    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(input);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throwsIllegalArgumentExceptionIfPointInInputIsRepeated() throws Exception {
    Point[] input = new Point[] { new Point(0, 0), new Point(0, 0), new Point(0, 0) };
    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(input);
  }
}
