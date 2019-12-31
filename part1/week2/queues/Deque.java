import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Deque class model.
 *
 * @author Alessio Vallero
 */
public class Deque<Item> implements Iterable<Item> {
    private DequeLinkedListNode head, tail;
    private int size;

    /**
     * Inner Node class for the double linked list used by the Deque class.
     *
     * @author Alessio Vallero
     */
    private class DequeLinkedListNode {
        public Item value;
        public DequeLinkedListNode prev;
        public DequeLinkedListNode next;
    }

    /**
     * Iterator for the Deque class
     *
     * @author Alessio Vallero
     */
    private class DequeIterator implements Iterator<Item> {
        private DequeLinkedListNode currentItem;

        public DequeIterator() {
            this.currentItem = head;
        }

        @Override
        public boolean hasNext() {
            return this.currentItem != null;
        }

        @Override
        public Item next() {
            if (this.currentItem == null) throw new NoSuchElementException();

            // Increment the iterator
            Item nextItem = currentItem.value;
            currentItem = currentItem.next;

            return nextItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not implemented.");
        }
    }

    /**
     * Construct an empty deque with null references to head and tail
     */
    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Is the deque empty?
     *
     * @return true if the deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items in the deque
     *
     * @return The number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Add the item to the front
     *
     * @param item The item to add to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // Create a new node with empty prev and old head as next
        DequeLinkedListNode newNode = new DequeLinkedListNode();
        newNode.value = item;
        newNode.prev = null;
        newNode.next = head;

        // If the head is null, the new node is going to be the tail too
        if (head == null) {
            tail = newNode;
        }
        else {
            // The previous head's prev is going to be the new node
            head.prev = newNode;
        }
        head = newNode;

        size++;
    }

    /**
     * Add the item to the back
     *
     * @param item The item to add to the back
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // Create a new node with empty next and old tail as prev
        DequeLinkedListNode newNode = new DequeLinkedListNode();
        newNode.value = item;
        newNode.prev = tail;
        newNode.next = null;

        // If the tail is null, the new node is going to be the head too
        if (tail == null) {
            head = newNode;
        }
        else {
            // The previous tail's next is going to be the new node
            tail.next = newNode;
        }
        tail = newNode;

        size++;
    }

    /**
     * Remove and return the item from the front
     *
     * @return The item removed from the front
     */
    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException();

        DequeLinkedListNode oldHead = head;
        Item retItem = oldHead.value;

        // The new head is going to be the current second element
        // The prev element of the new head becomes nobody (null)
        if (oldHead.next != null) {
            DequeLinkedListNode newHead = oldHead.next;
            newHead.prev = null;

            // Delete old head's next pointer to avoid zombie references
            oldHead.next = null;

            head = newHead;
        }
        else {
            head = null;
        }

        size--;

        // Clear tail if head was the tail too
        if (size == 0 && tail != null) {
            tail.prev = null; // Loitering
            tail = null;
        }

        return retItem;
    }

    /**
     * Remove and return the item from the back
     *
     * @return The item removed from the back
     */
    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException();

        DequeLinkedListNode oldTail = tail;
        Item retItem = oldTail.value;

        // The new tail is going to be the current tail's previous element
        // The next element of the new tail becomes nobody (null)
        if (oldTail.prev != null) {
            DequeLinkedListNode newTail = oldTail.prev;
            newTail.next = null;

            // Delete old tail's prev pointer to avoid zombie references
            oldTail.prev = null;
            tail = newTail;
        }
        else {
            tail = null;
        }

        size--;

        // Clear head if tail was the head too
        if (size == 0 && head != null) {
            head.next = null; // Loitering
            head = null;
        }

        return retItem;
    }

    /**
     * Return an iterator over items in order from front to back
     *
     * @return The iterator over items in order from front to back
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * Print the content of the Deque using an iterator
     */
    private void printDeque() {
        // Print Deque using Iterator
        System.out.print("Current deque content: ");
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
        Deque<Integer> deque = new Deque<Integer>();
        deque.isEmpty();
        deque.isEmpty();
        deque.addFirst(3);
        deque.addFirst(4);
        deque.removeLast();
        deque.removeLast();
        deque.addFirst(7);
        deque.removeLast();
    }
}
