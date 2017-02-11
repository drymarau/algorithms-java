import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public final class PercolationTest {

  private static final int N = 1000;

  @Test(expected = IllegalArgumentException.class)
  public void nLessThanOneThrowsIllegalArgumentException() throws Exception {
    new Percolation(0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void indexOutOfBoundsIsThrownWhenColumnOrRowIsOutOfBoundsInOpen() {
    Percolation p = new Percolation(N);
    p.open(0, N + 1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void indexOutOfBoundsIsThrownWhenColumnOrRowIsOutOfBoundsInIsOpen() {
    Percolation p = new Percolation(N);
    p.isOpen(N + 1, N + 1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void indexOutOfBoundsIsThrownWhenColumnOrRowIsOutOfBoundsInIsFull() {
    Percolation p = new Percolation(N);
    p.isFull(N + 1, N + 1);
  }

  @Test public void percolatesWhenOneColumnIsCompletelyOpen() throws Exception {
    Percolation p = new Percolation(N);
    for (int i = 1; i <= N; i++) {
      assertThat(p.percolates()).isFalse();
      p.open(i, 1);
      assertThat(p.isOpen(i, 1)).isTrue();
      assertThat(p.isFull(i, 1)).isTrue();
    }
    assertThat(p.percolates()).isTrue();
  }
}