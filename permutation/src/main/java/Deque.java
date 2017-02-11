import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Deque<Item> implements Iterable<Item> {

  private final Node head;
  private final Node tail;

  private int n = 0;

  public Deque() {
    this.head = new Node();
    this.tail = new Node();
    this.head.next = this.tail;
    this.tail.prev = this.head;
  }

  public boolean isEmpty() {
    return n == 0;
  }

  public int size() {
    return n;
  }

  public void addFirst(Item item) {
    checkNotNull(item, "item == null.");
    Node oldFirst = head.next;
    head.next = new Node();
    oldFirst.prev = head.next;
    head.next.item = item;
    head.next.next = oldFirst;
    head.next.prev = head;
    n++;
  }

  public void addLast(Item item) {
    checkNotNull(item, "item == null.");
    Node oldLast = tail.prev;
    tail.prev = new Node();
    oldLast.next = tail.prev;
    tail.prev.item = item;
    tail.prev.prev = oldLast;
    tail.prev.next = tail;
    n++;
  }

  public Item removeFirst() {
    checkEmpty();
    Node oldFirst = head.next;
    Node newFirst = oldFirst.next;
    oldFirst.next = null;
    oldFirst.prev = null;
    newFirst.prev = head;
    head.next = newFirst;
    n--;
    return oldFirst.item;
  }

  public Item removeLast() {
    checkEmpty();
    Node oldLast = tail.prev;
    Node newLast = oldLast.prev;
    oldLast.prev = null;
    oldLast.next = null;
    newLast.next = tail;
    tail.prev = newLast;
    n--;
    return oldLast.item;
  }

  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private final class DequeIterator implements Iterator<Item> {

    private Node current = head.next;

    public boolean hasNext() {
      return current != tail;
    }

    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException("no more items.");
      }
      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException("remove() is not supported.");
    }
  }

  private final class Node {

    private Item item;
    private Node next;
    private Node prev;
  }

  private static <T> T checkNotNull(T object, String message) {
    if (object == null) {
      throw new NullPointerException(message);
    }
    return object;
  }

  private void checkEmpty() {
    if (isEmpty()) {
      throw new NoSuchElementException("deque is empty.");
    }
  }

  @Override public String toString() {
    Item[] items = (Item[]) new Object[n];
    Iterator<Item> iterator = iterator();
    int i = 0;
    while (iterator.hasNext()) {
      items[i++] = iterator.next();
    }
    return Arrays.toString(items);
  }
}