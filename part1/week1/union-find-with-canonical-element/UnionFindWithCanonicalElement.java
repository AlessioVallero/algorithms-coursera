import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * The UnionFindWithCanonicalElement class model.
 *
 * @author Alessio Vallero
 */
public class UnionFindWithCanonicalElement {
    private int[] indexesOfMaxOfSet;
    private int[] id;    // id[i] = parent of i
    private int[] sz;    // sz[i] = number of objects in subtree rooted at i
    private int count;   // number of components

    /**
     * Create an empty union find data structure with n isolated sets.
     *
     * @param n Size of the array
     */
    public UnionFindWithCanonicalElement(int n) {
        count = n;
        indexesOfMaxOfSet = new int[n];
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            indexesOfMaxOfSet[i] = i;
            id[i] = i;
            sz[i] = 1;
        }
    }

    /**
     * Return the number of disjoint sets.
     * @return Number of disjoint sets
     */
    public int count() {
        return count;
    }

    /**
     * Return root for component containing p
     * @param p Component for which return the root element
     * @return Root element of p
     */
    public int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

    /**
     * Return max element for the set where p is contained
     * @param p Element for whom search the max of its set
     * @return Max element
     */
    public int findMax(int p) {
        return indexesOfMaxOfSet[find(p)];
    }

    /**
     * Are objects p and q in the same set?
     * @param p First object
     * @param q Second object
     * @return true if p and q are in the same set, false otherwise.
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Replace sets containing p and q with their union.
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        int maxOfi = indexesOfMaxOfSet[i];
        int maxOfj = indexesOfMaxOfSet[j];

        // make smaller root point to larger one
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];

            if( maxOfi > maxOfj ) {
                indexesOfMaxOfSet[j] = indexesOfMaxOfSet[i];
            }
        }
        else {
            id[j] = i;
            sz[i] += sz[j];

            if( maxOfj > maxOfi ) {
                indexesOfMaxOfSet[i] = indexesOfMaxOfSet[j];
            }
        }

        count--;
    }

    /**
     * Test client
     *
     * @param args Proper Usage is: UnionFindWithCanonicalElement <ARRAY_SIZE> <TRIALS_N>
     */
    public static void main(String[] args) {
        // Check how many arguments were passed in
        if (args.length < 2) {
            System.out.println("Proper Usage is: UnionFindWithCanonicalElement <ARRAY_SIZE> <TRIALS>");
        }
        else {
            Stopwatch stopwatch = new Stopwatch();

            int randomConnectCount = StdRandom.uniform(1, Integer.parseInt(args[0]) / 2);

            System.out.println("Will connect " + randomConnectCount + " elements." );

            // For each trial, we create a new array and connect objects for randomConnectCount times
            for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                UnionFindWithCanonicalElement unionFindWithCanonicalElement = new UnionFindWithCanonicalElement(Integer.parseInt(args[0]));

                int count = 0;
                do {
                    int randomFirst = StdRandom.uniform(0, Integer.parseInt(args[0]));
                    int randomSecond = StdRandom.uniform(0, Integer.parseInt(args[0]));

                    // Do not connect with same element
                    if (randomFirst == randomSecond) continue;

                    unionFindWithCanonicalElement.union(randomFirst, randomSecond);

                    System.out.println("Connected (" + randomFirst + ", " + randomSecond + ")" );

                    count++;
                } while (count < randomConnectCount);

                // Print the resulting max for each element
                for( int j = 0 ; j < Integer.parseInt(args[0]) ; j++) {
                    int maxInSet = unionFindWithCanonicalElement.findMax(j);
                    if (maxInSet > -1) {
                        System.out.println("Max of element " + j + " is element " + maxInSet );
                    }
                    else {
                        System.out.println("No successor for element " + j);
                    }
                }
            }

            System.out.println("elapsed time: " + stopwatch.elapsedTime());
        }
    }
}
