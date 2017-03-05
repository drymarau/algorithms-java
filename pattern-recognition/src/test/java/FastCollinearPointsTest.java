import org.junit.Test;

public final class FastCollinearPointsTest {

  @Test(expected = NullPointerException.class) public void throwsNullPointerExceptionIfInputIsNull()
      throws Exception {
    FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(null);
  }

  @Test(expected = NullPointerException.class)
  public void throwsNullPointerExceptionIfPointInInputIsNull() throws Exception {
    Point[] input = new Point[] { null, null, null };
    FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(input);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throwsIllegalArgumentExceptionIfPointInInputIsRepeated() throws Exception {
    Point[] input = new Point[] { new Point(0, 0), new Point(0, 0), new Point(0, 0) };
    FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(input);
  }
}
