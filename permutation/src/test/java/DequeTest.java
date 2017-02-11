import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public final class DequeTest {

  private Deque<Integer> deque;

  @Before public void setUp() throws Exception {
    deque = new Deque<>();
  }

  @Test(expected = NullPointerException.class)
  public void throwsNullPointerExceptionWhenAddingFirstNullItem() throws Exception {
    deque.addFirst(null);
  }

  @Test(expected = NullPointerException.class)
  public void throwsNullPointerExceptionWhenAddingLastNullItem() throws Exception {
    deque.addLast(null);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void throwsUnsupportedOperationExceptionWhenRemoveCalledInIterator() throws Exception {
    Iterator<Integer> iterator = deque.iterator();
    assertThat(iterator).isNotNull();
    iterator.remove();
  }

  @Test(expected = NoSuchElementException.class) public void emptyIteratorThrows()
      throws Exception {
    Iterator<Integer> iterator = deque.iterator();
    iterator.next();
  }

  @Test public void addFirstAddsToDequeAndRemoveFirstReturnsThisItem() throws Exception {
    int expected = 42;
    assertThat(deque.isEmpty()).isTrue();
    deque.addFirst(expected);
    assertThat(deque.size()).isEqualTo(1);
    assertThat(deque.removeFirst()).isEqualTo(expected);
  }

  @Test public void addLastAddsToDequeAndRemoveLastReturnsThisItem() throws Exception {
    int expected = 42;
    assertThat(deque.isEmpty()).isTrue();
    deque.addLast(expected);
    assertThat(deque.size()).isEqualTo(1);
    assertThat(deque.removeLast()).isEqualTo(expected);
  }

  @Test public void addAndRemoveInAnyOrderReturnValidItems() throws Exception {
    int expected = 42;
    assertThat(deque.isEmpty()).isTrue();
    deque.addFirst(expected);
    assertThat(deque.size()).isEqualTo(1);
    assertThat(deque.removeFirst()).isEqualTo(expected);
    deque.addFirst(expected);
    assertThat(deque.size()).isEqualTo(1);
    assertThat(deque.removeLast()).isEqualTo(expected);
    deque.addLast(expected);
    assertThat(deque.size()).isEqualTo(1);
    assertThat(deque.removeFirst()).isEqualTo(expected);
  }

  @Test(expected = NoSuchElementException.class)
  public void attemptToRemoveFirstInEmptyDequeThrows() {
    deque.removeFirst();
  }

  @Test(expected = NoSuchElementException.class)
  public void attemptToRemoveLastInEmptyDequeThrows() {
    deque.removeLast();
  }
}
