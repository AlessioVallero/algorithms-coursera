import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The RandomizedQueue class model.
 *
 * @author Alessio Vallero
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size, capacity;
    private Item[] arQueue;

    /**
     * Iterator for the RandomizedQueue class
     *
     * @author Alessio Vallero
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] clonedItems;
        private int remainingItems;

        public RandomizedQueueIterator() {
            clonedItems = arQueue.clone();
            remainingItems = size;
        }

        @Override
        public boolean hasNext() {
            return remainingItems > 0;
        }

        @Override
        public Item next() {
            if (remainingItems == 0) throw new NoSuchElementException();

            // Generate a uniform random index to pick an item
            int randomItem = StdRandom.uniform(0, remainingItems);
            Item nextItem = clonedItems[randomItem];

            // If the random index is not the last element, we also swap the last element into the random item
            // to avoid holes in the clonedItems
            if (randomItem != remainingItems - 1) {
                clonedItems[randomItem] = clonedItems[remainingItems - 1];
            }
            // To avoid loitering
            clonedItems[remainingItems - 1] = null;
            // Decrease the remainingItems count
            remainingItems--;

            return nextItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not implemented.");
        }
    }

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        size = 0;
        capacity = 1;

        arQueue = (Item[]) new Object[capacity];
    }

    /**
     * Is the randomized queue empty?
     *
     * @return true if the randomized queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items in randomized queue
     *
     * @return The number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Resize the randomized queue with a provided new capacity. Copies the old element over after
     * resizing
     *
     * @param newCapacity New capacity of the randomized queue
     */
    private void resize(int newCapacity) {
        if (newCapacity > 0) {
            Item[] newArQueue = (Item[]) new Object[newCapacity];

            // Copy old elements in new, resized, queue
            for (int i = 0; i < size; i++) {
                newArQueue[i] = arQueue[i];
            }

            arQueue = newArQueue;
            capacity = newCapacity;
        }
    }

    /**
     * Add the item
     *
     * @param item Item to add to the randomized queue
     */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // If the size has reached the max capacity, we double the queue size
        if (size == capacity) {
            resize(capacity * 2);
        }

        arQueue[size++] = item;
    }

    /**
     * Remove and return a random item
     *
     * @return A random item
     */
    public Item dequeue() {
        if (size < 1) throw new NoSuchElementException();

        // Generate a uniform random index to pick an item
        int randomItem = StdRandom.uniform(0, size);
        Item retItem = arQueue[randomItem];

        // If the random index is not the last element, we also swap the last element into the random item
        // to avoid holes in the queue
        if (randomItem != size - 1) {
            arQueue[randomItem] = arQueue[size - 1];
        }
        // To avoid loitering
        arQueue[size - 1] = null;
        // Decrease the queue size
        size--;

        // If the size of the queue has reached less than 25% of the total space, we halves the capacity
        // to optimize space usage
        if (size < capacity / 4) {
            resize(capacity / 2);
        }

        return retItem;
    }

    /**
     * Return a random item (but do not remove it)
     *
     * @return A random item
     */
    public Item sample() {
        if (size < 1) throw new NoSuchElementException();

        int randomItem = StdRandom.uniform(0, size);

        return arQueue[randomItem];
    }

    /**
     * Return an independent iterator over items in random order
     *
     * @return The iterator over items in order from front to back
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * Print the content of the RandomizedQueue using an iterator
     */
    private void printRandomizedQueue() {
        // Print RandomizedQueue using Iterator
        System.out.print("Current RandomizedQueue content: ");
        for (Object value : this) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    /**
     * Unit testing (required)
     *
     * @param args No argument is necessary
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        for (int i = 0; i < 10; i++) {
            int newRandomItem = StdRandom.uniform(-100, 100);

            // Add item
            randomizedQueue.enqueue(newRandomItem);
            System.out.println("enqueue: " + newRandomItem);
            // Add item
            randomizedQueue.enqueue(-newRandomItem);
            System.out.println("enqueue: " + (-newRandomItem));
            // Print size and isEmpty
            System.out.println(
                    "RandomizedQueue size: " + randomizedQueue.size() + " RandomizedQueue isEmpty: "
                            + randomizedQueue.isEmpty());
            // Print current status of RandomizedQueue using the iterator
            randomizedQueue.printRandomizedQueue();
            // Remove random element from the RandomizedQueue
            System.out.println("dequeue: " + randomizedQueue.dequeue());
            // Print current status of RandomizedQueue using the iterator
            randomizedQueue.printRandomizedQueue();
            // Add item
            randomizedQueue.enqueue(newRandomItem * 10);
            System.out.println("enqueue: " + newRandomItem * 10);
            // Remove last element in the RandomizedQueue
            System.out.println("dequeue: " + randomizedQueue.dequeue());
            // Print current status of RandomizedQueue using the iterator
            randomizedQueue.printRandomizedQueue();
            System.out.println();
        }
    }
}
