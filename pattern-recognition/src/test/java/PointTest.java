import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public final class PointTest {

  private Point point;

  @Before public void setUp() throws Exception {
    point = new Point(0, 0);
  }

  @Test public void comparesPointsByYCoordinatesBreakingTiesByXCoordinates() throws Exception {
    // Point is equal to reference point
    Point testPoint = new Point(0, 0);
    assertThat(point).isEquivalentAccordingToCompareTo(testPoint);
    // Point is greater than reference point
    testPoint = new Point(0, -1);
    assertThat(point).isGreaterThan(testPoint);
    // Point is lesser than reference point
    testPoint = new Point(0, 1);
    assertThat(point).isLessThan(testPoint);
    // Point is greater than reference point
    testPoint = new Point(-1, 0);
    assertThat(point).isGreaterThan(testPoint);
    // Point is greater than reference point
    testPoint = new Point(1, 0);
    assertThat(point).isLessThan(testPoint);
  }

  @Test public void slopeToTreatsHorizontalLineAsPositiveZero() throws Exception {
    Point testPoint = new Point(1, 0);
    assertThat(point.slopeTo(testPoint)).isZero();
    testPoint = new Point(-1, 0);
    assertThat(point.slopeTo(testPoint)).isZero();
  }

  @Test public void slopeToTreatsVerticalLineAsPositiveInfinity() throws Exception {
    Point testPoint = new Point(0, 1);
    assertThat(point.slopeTo(testPoint)).isPositiveInfinity();
    testPoint = new Point(0, -1);
    assertThat(point.slopeTo(testPoint)).isPositiveInfinity();
  }

  @Test public void slopeToTreatsDegenerateLineAsNegativeInfinity() throws Exception {
    Point testPoint = point;
    assertThat(point.slopeTo(testPoint)).isNegativeInfinity();
  }

  @Test public void slopeToCalculatesSlopeCorrectly() throws Exception {
    // Slope is equal to 1
    Point testPoint = new Point(1, 1);
    assertThat(point.slopeTo(testPoint)).isEqualTo(1d);
    // Slope is equal to 0.5
    testPoint = new Point(2, 1);
    assertThat(point.slopeTo(testPoint)).isEqualTo(0.5);
    // Slope is equal to -0.5
    testPoint = new Point(-4, 2);
    assertThat(point.slopeTo(testPoint)).isEqualTo(-0.5);
    // Slope is equal to -1
    testPoint = new Point(2, -2);
    assertThat(point.slopeTo(testPoint)).isEqualTo(-1d);
  }

  @Test public void slopeOrderComparesPointsCorrectly() throws Exception {
    // Slope == 1
    Point p1 = new Point(1, 1);
    // Slope == 1
    Point p2 = new Point(1, 1);
    assertThat(point.slopeOrder().compare(p1, p2)).isEqualTo(0);
    // Slope == 1
    p1 = new Point(1, 1);
    // Slope == 2
    p2 = new Point(1, 2);
    assertThat(point.slopeOrder().compare(p1, p2)).isLessThan(0);
    // Slope == 2
    p1 = new Point(1, 2);
    // Slope == 1
    p2 = new Point(1, 1);
    assertThat(point.slopeOrder().compare(p1, p2)).isGreaterThan(0);
    // Slope == Double.POSITIVE_INFINITY
    p1 = new Point(0, 2);
    // Slope == 1
    p2 = new Point(1, 1);
    assertThat(point.slopeOrder().compare(p1, p2)).isGreaterThan(0);
    // Slope == Double.NEGATIVE_INFINITY
    p1 = point;
    // Slope == 1
    p2 = new Point(1, 1);
    assertThat(point.slopeOrder().compare(p1, p2)).isLessThan(0);
  }
}
