import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
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

      int n = 0;
      for (int i = 0; i < elements.length; i++) {
        n = StdRandom.uniform(elements.length + 1);
        Item temp = elements[i];
        elements[i] = elements[n];
        elements[n] = temp;
      }

      current = 0;
    }

    public boolean hasNext() {
      return current <= elements.length - 1;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      return elements[current];
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
    Item[] copy = (Item[]) new Object[es.length];
    for (int i = 0; i < es.length; i++) {
      if (i < n) {
        copy[i] = es[i];
      } else if (i > n) {
        copy[i - 1] = es[i];
      }
    }

    Item res = es[n];
    es = copy;
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

    return es[StdRandom.uniform(pos + 1)];
  }

  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < es.length; i++) {
      copy[i] = es[i];
    }
    es = copy;
  }
}
