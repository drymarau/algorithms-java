import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public final class PercolationStatsTest {

  @Test(expected = IllegalArgumentException.class) public void validatesNIsGreaterThanZero()
      throws Exception {
    new PercolationStats(0, Integer.MAX_VALUE);
  }

  @Test(expected = IllegalArgumentException.class) public void validatesTrialsIsGreaterThanZero()
      throws Exception {
    new PercolationStats(Integer.MAX_VALUE, 0);
  }

  @Test public void valuesMatchKnownValues() throws Exception {
    // n = 200, T = 100
    PercolationStats stats = new PercolationStats(200, 100);
    assertThat(stats.mean()).isWithin(0.02).of(0.590);
    assertThat(stats.stddev()).isWithin(0.002).of(0.009);
    assertThat(stats.confidenceLo()).isWithin(0.01).of(0.59);
    assertThat(stats.confidenceHi()).isWithin(0.01).of(0.59);
  }
}