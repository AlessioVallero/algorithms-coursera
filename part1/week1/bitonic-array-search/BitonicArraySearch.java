package bitonicarraysearch;

import edu.princeton.cs.algs4.StdRandom;

import java.util.HashSet;
import java.util.Set;

/**
 * The BitonicArraySearch class model.
 *
 * @author Alessio Vallero
 */
public class BitonicArraySearch {
    private final int[] bitonicArray;
    private final int bitonicPoint;

    /**
     * Clone a bitonic array, compute and store its bitonic point. The input array is assumed to be
     * bitonic and with all distinct values by construction. An IllegalArgumentException is thrown
     * if the bitonic point is not found.
     *
     * @param bitonicArray The bitonic array
     */
    public BitonicArraySearch(int[] bitonicArray) {
        this.bitonicArray = bitonicArray.clone();
        bitonicPoint = indexOfBitonicPoint();

        if (bitonicPoint > -1) {
            System.out.println("Bitonic point is " + this.bitonicArray[bitonicPoint]);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Search the bitonic point of the array stored in the object using a customized binary search
     *
     * @return The index of the bitonic point. -1 otherwise.
     */
    private int indexOfBitonicPoint() {
        int lo = 0;
        int hi = bitonicArray.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (bitonicArray[mid - 1] < bitonicArray[mid]) {
                if (bitonicArray[mid] > bitonicArray[mid + 1]) {
                    return mid;
                }

                lo = mid + 1;
            }
            else {
                hi = mid - 1;
            }
        }

        return -1;
    }

    /**
     * Search for a value in the bitonic array stored in the object
     *
     * @param value Value to search in the bitonic array
     * @return If found, the index of the value in the array, -1 otherwise
     */
    private int bitonicSearch(int value) {
        // If the value is greater than the bitonic point, it cannot be found
        // because all elements in both left and right are smaller than the bitonic point
        if (bitonicArray[bitonicPoint] < value) return -1;

        // If the bitonicPoint is the value, we return immediately with it
        if (bitonicArray[bitonicPoint] == value) return bitonicPoint;
        else {
            int ascLo = 0;
            int ascHi = bitonicPoint - 1;

            int descLo = bitonicPoint + 1;
            int descHi = bitonicArray.length - 1;

            // Search in both ascendant and descendant sides of the array.
            // The bitonic point is excluded because we've checked above already
            while (ascLo <= ascHi || descLo <= descHi) {
                // Ascendant side
                int ascMid = ascLo + (ascHi - ascLo) / 2;
                if (bitonicArray[ascMid] == value) return ascMid;
                if (bitonicArray[ascMid] > value) ascHi = ascMid - 1;
                else ascLo = ascMid + 1;
                // Descendant side
                int descMid = descLo + (descHi - descLo) / 2;
                if (bitonicArray[descMid] == value) return descMid;
                if (bitonicArray[descMid] < value) descHi = descMid - 1;
                else descLo = descMid + 1;
            }
        }

        // Not found
        return -1;
    }

    /**
     * Test client
     *
     * @param args Proper Usage is: BitonicArraySearch <ARRAY_SIZE> <TRIALS>
     */
    public static void main(String[] args) {
        // Check how many arguments were passed in
        if (args.length < 2) {
            System.out.println(
                    "Proper Usage is: BitonicArraySearch <ARRAY_SIZE> <TRIALS>");
        }
        else {
            final int arraySize = Integer.parseInt(args[0]);
            final int trials = Integer.parseInt(args[1]);

            // For each trial, we create a random bitonic array and search a random value
            for (int i = 0; i < trials; i++) {
                Set<Integer> arrayElements = new HashSet<>();

                int[] bitonicArray = new int[arraySize];
                // Bitonic point is in the middle
                int randomBitonicPointIndex = arraySize / 2;
                // Random bitonic point value
                int randomBitonicPointValue = arraySize * 100;
                bitonicArray[randomBitonicPointIndex] = randomBitonicPointValue;

                int prevAsc = 0, prevDesc = 0;
                for (int j = 0; j < randomBitonicPointIndex; j++) {
                    // Use the HashSet to be assured to create distinct elements in both sides
                    do {
                        prevAsc = StdRandom.uniform(prevAsc + 1, prevAsc + arraySize * 10);
                    }
                    while (arrayElements.contains(prevAsc));

                    do {
                        prevDesc = StdRandom.uniform(prevDesc + 1, prevDesc + arraySize * 10);
                    }
                    while (arrayElements.contains(prevDesc));

                    arrayElements.add(prevAsc);
                    arrayElements.add(prevDesc);

                    // Left side
                    bitonicArray[j] = prevAsc;
                    // Right side
                    bitonicArray[arraySize - 1 - j] = prevDesc;
                }

                // Print array
                System.out.print("Created array: [");
                for (int j = 0; j < arraySize - 1; j++) {
                    System.out.print(bitonicArray[j] + ", ");
                }
                System.out.print(bitonicArray[arraySize - 1] + "] ");

                BitonicArraySearch bitonicArraySearch = new BitonicArraySearch(bitonicArray);

                // 3 search
                for (int j = 0; j < 3; j++) {
                    int randomValueToSearch = bitonicArray[StdRandom.uniform(0, arraySize)];
                    System.out.println("Will search value: " + randomValueToSearch);

                    long start = System.nanoTime();
                    int indexFound = bitonicArraySearch.bitonicSearch(randomValueToSearch);
                    long finish = System.nanoTime();
                    
                    if (indexFound > -1) {
                        System.out.println("Found value: "
                                                   + bitonicArray[indexFound]);
                    }
                    else {
                        System.out.println("Element not found!");
                    }

                    System.out
                            .println("Elapsed time for search in nanoseconds: " + (finish - start));
                }

                System.out.println("");
            }
        }
    }
}
