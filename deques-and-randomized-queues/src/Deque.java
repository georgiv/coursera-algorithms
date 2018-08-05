import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private class Node {
    Item item;
    Node previous;
    Node next;
  }

  private class DequeIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  private Node first, last;
  private int count;

  public Deque() {
    last = first = new Node();
  }

  public boolean isEmpty() {
    return first.item == null;
  }

  public int size() {
    return count;
  }

  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (isEmpty()) {
      first = new Node();
      first.item = item;
    } else {
      Node oldFirst = first;
      first = new Node();
      first.item = item;
      first.next = oldFirst;
      oldFirst.previous = first;
      count++;
    }
  }

  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (isEmpty()) {
      last = new Node();
      last.item = item;
    } else {
      Node oldLast = last;
      last = new Node();
      last.item = item;
      last.previous = oldLast;
      oldLast.next = last;
      count++;
    }
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Node oldFirst = first;
    first = oldFirst.next;
    first.previous = null;
    count--;
    return oldFirst.item;
  }

  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Node oldLast = last;
    last = oldLast.previous;
    last.next = null;
    count--;
    return oldLast.item;
  }

  public Iterator<Item> iterator() {
    return new DequeIterator();
  }
}
