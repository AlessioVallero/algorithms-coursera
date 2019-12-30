package threesum;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The ThreeSum class model.
 *
 * @author Alessio Vallero
 */
public class ThreeSum {
    private final int[] arThreeSum;

    /**
     * Clone a sorted array to compute the 3-sum with. An IllegalArgumentException is thrown if
     * there are less than 3 items in the array.
     *
     * @param array The sorted array
     */
    public ThreeSum(int[] array) {
        if (array.length < 3) {
            throw new IllegalArgumentException();
        }

        arThreeSum = array.clone();
    }

    /**
     * Print each triple of numbers that sums to 0. O(n^2) complexity
     */
    public void printThreeSum() {
        // For each element, scan the remaining part of the array for pairs that sums to 0 with the current element
        for (int i = 0; i < arThreeSum.length - 1; i++) {
            int j = i + 1, k = arThreeSum.length - 1;
            while (j < k) {
                int nextSum = arThreeSum[i] + arThreeSum[j] + arThreeSum[k];
                if (nextSum == 0) System.out.println(
                        "New 3-sum found: " + arThreeSum[i] + " " + arThreeSum[j] + " "
                                + arThreeSum[k]);

                // Since the array is sorted, if the sum is greater than 0 we decrement the right index to decrement the sum
                // If the sum is smaller than 0, we increment the left side to increment the sum instead
                if (nextSum <= 0) j++;
                else k--;
            }
        }
    }

    public static void main(String[] args) {
        // Check how many arguments were passed in
        if (args.length < 2) {
            System.out.println("Proper Usage is: ThreeSum <ARRAY_SIZE> <TRIALS>");
        }
        else {
            final int arraySize = Integer.parseInt(args[0]);
            final int trials = Integer.parseInt(args[1]);

            // For each trial, we create a random array and search for triples that sums to 0
            for (int i = 0; i < trials; i++) {
                Set<Integer> arrayElements = new HashSet<>();

                // Create random array
                int[] newArray = new int[arraySize];
                for (int j = 0; j < arraySize; j++) {
                    int newRandomItem;
                    do {
                        newRandomItem = StdRandom.uniform(-arraySize, arraySize);
                    }
                    while (arrayElements.contains(newRandomItem));

                    newArray[j] = newRandomItem;
                    arrayElements.add(newRandomItem);
                }
                // Sort array
                Arrays.sort(newArray);

                // Print array
                System.out.print("Created array: [");
                for (int j = 0; j < arraySize - 1; j++) {
                    System.out.print(newArray[j] + ", ");
                }
                System.out.println(newArray[arraySize - 1] + "] ");

                ThreeSum threeSum = new ThreeSum(newArray);

                Stopwatch stopwatch = new Stopwatch();
                // Print 3-sums to 0, if any
                threeSum.printThreeSum();
                System.out.println("Elapsed time: " + stopwatch.elapsedTime());

                System.out.println();
            }
        }
    }
}
