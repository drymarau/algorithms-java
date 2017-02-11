import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public final class RandomizedQueueTest {

  private static final int N = 10000;

  private RandomizedQueue<Integer> randomizedQueue;

  @Before public void setUp() throws Exception {
    randomizedQueue = new RandomizedQueue<>();
    assertThat(randomizedQueue.isEmpty()).isTrue();
  }

  @Test(expected = NullPointerException.class) public void addingNullItemThrows() throws Exception {
    randomizedQueue.enqueue(null);
  }

  @Test(expected = NoSuchElementException.class)
  public void samplingItemFromEmptyRandomizedQueueThrows() throws Exception {
    randomizedQueue.sample();
  }

  @Test(expected = NoSuchElementException.class)
  public void dequeuingItemFromEmptyRandomizedQueueThrows() throws Exception {
    randomizedQueue.dequeue();
  }

  @Test(expected = UnsupportedOperationException.class) public void removingFromIteratorThrows()
      throws Exception {
    Iterator<Integer> iterator = randomizedQueue.iterator();
    assertThat(iterator).isNotNull();
    iterator.remove();
  }

  @Test(expected = NoSuchElementException.class) public void callingNextOnEmptyIteratorThrows()
      throws Exception {
    Iterator<Integer> iterator = randomizedQueue.iterator();
    assertThat(iterator).isNotNull();
    iterator.next();
  }

  @Test public void addingItemsChangeSize() throws Exception {
    randomizedQueue.enqueue(1);
    assertThat(randomizedQueue.size()).isEqualTo(1);
    randomizedQueue.enqueue(2);
    assertThat(randomizedQueue.size()).isEqualTo(2);
  }

  @Test public void samplingCollectionReturnsRandomItem() throws Exception {
    List<Integer> values = new ArrayList<>(N);
    for (int i = 0; i < N; i++) {
      values.add(i, StdRandom.uniform(Integer.MAX_VALUE));
      randomizedQueue.enqueue(values.get(i));
    }
    assertThat(randomizedQueue.size()).isEqualTo(values.size());
    assertThat(randomizedQueue.sample()).isIn(values);
    assertThat(randomizedQueue.size()).isEqualTo(values.size());
  }

  @Test public void dequeuingCollectionRemovesRandomItem() throws Exception {
    List<Integer> values = new ArrayList<>(N);
    for (int i = 0; i < N; i++) {
      values.add(i, StdRandom.uniform(Integer.MAX_VALUE));
      randomizedQueue.enqueue(values.get(i));
      assertThat(randomizedQueue.size()).isEqualTo(i + 1);
    }
    for (int i = 0; i < N; i++) {
      assertThat(randomizedQueue.dequeue()).isIn(values);
      assertThat(randomizedQueue.size()).isEqualTo(N - i - 1);
    }
  }
}
