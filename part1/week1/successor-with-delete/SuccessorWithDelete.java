import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

/**
 * The SuccessorWithDelete class model.
 *
 * @author Alessio Vallero
 */
public class SuccessorWithDelete {
    private final WeightedQuickUnionUF weightedQuickUnionDeleted;
    private final int[] arData;
    private final int[] arDataSuccessors;

    /**
     * Creates an n-sized array, with all successors initially set to the next element
     *
     * @param n size of the array
     */
    public SuccessorWithDelete(int n) {
        arData = new int[n];
        // Init successors
        arDataSuccessors = new int[n];
        for( int i = 0 ; i < n ; i++) {
            arData[i] = i;
            arDataSuccessors[i] = i + 1;
        }
        arDataSuccessors[n-1] = -1;

        weightedQuickUnionDeleted = new WeightedQuickUnionUF(n);
    }

    /**
     * Delete element i and adjust predecessor's successor
     * @param i Element index to delete
     */
    public void deleteElement(int i) {
        if( i > 0 ) {
            weightedQuickUnionDeleted.union(i, i-1);

            int root = weightedQuickUnionDeleted.find(i-1);
            if( i < arData.length - 1 ) {
                if (arData[arDataSuccessors[root]] < arData[i + 1]) {
                    arDataSuccessors[root] = i + 1;
                }
            }
            else {
                arDataSuccessors[root] = -1;
            }
        }
    }

    /**
     * Return the current successor of element i
     * @param i Index of the element for whom we want to return the successor
     * @return The successor's value
     */
    public int getSuccessor(int i) {
        int root = weightedQuickUnionDeleted.find(i);

        if( arDataSuccessors[root] == -1) return -1;

        return arData[arDataSuccessors[root]];
    }

    /**
     * Test client
     *
     * @param args Proper Usage is: SuccessorWithDelete <ARRAY_SIZE> <TRIALS_N>
     */
    public static void main(String[] args) {
        // Check how many arguments were passed in
        if (args.length < 2) {
            System.out.println("Proper Usage is: SuccessorWithDelete <ARRAY_SIZE> <TRIALS>");
        }
        else {
            Stopwatch stopwatch = new Stopwatch();

            int randomDeleteCount = StdRandom.uniform(1, Integer.parseInt(args[0]) / 2);

            System.out.println("Will delete " + randomDeleteCount + " elements." );

            // For each trial, we create a new array and delete objects for deleteCount times
            for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                Set<Integer> deletedElements = new HashSet<Integer>();

                SuccessorWithDelete successorWithDelete = new SuccessorWithDelete(Integer.parseInt(args[0]));

                int count = 0;
                do {
                    // Delete
                    int randomElem = - 1;
                    do {
                        randomElem = StdRandom.uniform(0, Integer.parseInt(args[0]));
                    }
                    while( deletedElements.contains(randomElem) );
                    successorWithDelete.deleteElement(randomElem);

                    deletedElements.add(randomElem);

                    System.out.println("Delete " + randomElem );

                    count++;
                } while (count < randomDeleteCount);

                // Print the resulting successors for each non-deleted element
                for( int j = 0 ; j < Integer.parseInt(args[0]) ; j++) {
                    if( !deletedElements.contains(j) ) {
                        int successor = successorWithDelete.getSuccessor(j);
                        if(successor > -1) {
                            System.out.println("Successor of element " + j + " is element " + successor );
                        }
                        else {
                            System.out.println("No successor for element " + j);
                        }
                    }
                }
            }

            System.out.println("elapsed time: " + stopwatch.elapsedTime());
        }
    }
}
