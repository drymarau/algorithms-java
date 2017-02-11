import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class RandomizedQueue<Item> implements Iterable<Item> {

  private int n = 0;
  private Item[] array = (Item[]) new Object[2];

  public boolean isEmpty() {
    return n == 0;
  }

  public int size() {
    return n;
  }

  public void enqueue(Item item) {
    checkNotNull(item, "item == null.");
    if (n == array.length) {
      resize(array.length * 2);
    }
    array[n++] = item;
  }

  private void resize(int capacity) {
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0; i < n; i++) {
      if (array[i] != null) {
        temp[i] = array[i];
      }
    }
    array = temp;
  }

  public Item dequeue() {
    checkEmpty();
    int i = randomIndex();
    Item item = array[i];
    array[i] = array[lastIndex()];
    array[lastIndex()] = null;
    n--;
    if (n > 0 && array.length / 4 == n) {
      resize(array.length / 2);
    }
    return item;
  }

  public Item sample() {
    checkEmpty();
    return array[randomIndex()];
  }

  private int randomIndex() {
    return StdRandom.uniform(n);
  }

  private int lastIndex() {
    return n - 1;
  }

  private static <T> T checkNotNull(T object, String message) {
    if (object == null) {
      throw new NullPointerException(message);
    }
    return object;
  }

  private void checkEmpty() {
    if (isEmpty()) {
      throw new NoSuchElementException("queue is empty.");
    }
  }

  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    private final int[] indices = new int[n];
    private int i = 0;

    public RandomizedQueueIterator() {
      for (int j = 0; j < indices.length; j++) {
        indices[j] = j;
      }
      StdRandom.shuffle(indices);
    }

    @Override public boolean hasNext() {
      return i < indices.length;
    }

    @Override public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException("no more items.");
      }
      return array[indices[i++]];
    }

    @Override public void remove() {
      throw new UnsupportedOperationException("remove() is not supported.");
    }
  }
}
