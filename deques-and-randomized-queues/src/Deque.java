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
      if (current == null) {
        throw new NoSuchElementException();
      }

      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  private Node first, last;
  private int count;

  public boolean isEmpty() {
    return first == null;
  }

  public int size() {
    return count;
  }

  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    Node oldFirst = first;
    first = new Node();
    first.item = item;
    if (oldFirst != null) {
      first.next = oldFirst;
      oldFirst.previous = first;
    } else {
      last = first;
    }
    count++;
  }

  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    Node oldLast = last;
    last = new Node();
    last.item = item;
    if (oldLast != null) {
      last.previous = oldLast;
      oldLast.next = last;
    } else {
      first = last;
    }
    count++;
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Node oldFirst = first;
    first = oldFirst.next;
    if (first == null) {
      last = null;
    } else {
      first.previous = null;
    }
    count--;
    return oldFirst.item;
  }

  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Node oldLast = last;
    last = oldLast.previous;
    if (last == null) {
      first = null;
    } else {
      last.next = null;
    }
    count--;
    return oldLast.item;
  }

  public Iterator<Item> iterator() {
    return new DequeIterator();
  }
}
