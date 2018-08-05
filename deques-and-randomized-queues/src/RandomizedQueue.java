import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private class Node {
  }

  private class DequeIterator implements Iterator<Item> {
    public boolean hasNext() {
      return false;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      return null;
    }
  }

  public RandomizedQueue() {
    // construct an empty randomized queue
  }

  public boolean isEmpty() {
    // is the randomized queue empty?
    return false;
  }

  public int size() {
    // return the number of items on the randomized queue
    return 0;
  }

  public void enqueue(Item item) {
    // add the item
  }

  public Item dequeue() {
    // remove and return a random item
    return null;
  }

  public Item sample() {
    // return a random item (but do not remove it)
    return null;
  }

  public Iterator<Item> iterator() {
    return null;
  }
}
