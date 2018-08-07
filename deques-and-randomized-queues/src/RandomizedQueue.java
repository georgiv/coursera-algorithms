import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] es;
  private int pos;

  private class RandomizedQueueIterator implements Iterator<Item> {
    private Item[] elements;
    private int current;

    public RandomizedQueueIterator() {
      elements = (Item[]) new Object[pos];
      for (int i = 0; i < elements.length; i++) {
        elements[i] = es[i];
      }

      for (int i = 0; i < elements.length; i++) {
        int n = StdRandom.uniform(i + 1);
        Item temp = elements[i];
        elements[i] = elements[n];
        elements[n] = temp;
      }

      current = 0;
    }

    public boolean hasNext() {
      return current < elements.length;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (current == elements.length) {
        throw new NoSuchElementException();
      }

      return elements[current++];
    }
  }

  public RandomizedQueue() {
    es = (Item[]) new Object[1];
    pos = 0;
  }

  public boolean isEmpty() {
    return pos == 0;
  }

  public int size() {
    return pos;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (pos == es.length) {
      resize(2 * es.length);
    }

    es[pos++] = item;
  }

  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    int n = StdRandom.uniform(pos);
    Item res = es[n];
    es[n] = es[pos - 1];
    es[pos - 1] = null;
    pos--;

    if (pos > 0 && pos == es.length / 4) {
      resize(es.length / 2);
    }

    return res;
  }

  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    return es[StdRandom.uniform(pos)];
  }

  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < pos; i++) {
      copy[i] = es[i];
    }
    es = copy;
  }
}
