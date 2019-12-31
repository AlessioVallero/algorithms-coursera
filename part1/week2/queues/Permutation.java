import edu.princeton.cs.algs4.StdIn;

/**
 * A client program for the RandomizedQueue class model.
 *
 * @author Alessio Vallero
 */
public class Permutation {
    public static void main(String[] args) {
        // Check how many arguments were passed in
        if (args.length < 1) {
            System.out.println("Proper Usage is: Permutation <ITEMS_COUNT_FROM_STDIN>");
        }
        else {
            final int elemToReadCount = Integer.parseInt(args[0]);

            RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

            while (!StdIn.isEmpty()) {
                String item = StdIn.readString();
                if (item != null && item.length() > 0) {
                    randomizedQueue.enqueue(item);
                }
            }

            for (int i = 0; i < elemToReadCount; i++) {
                System.out.println(randomizedQueue.dequeue());
            }
        }
    }
}
