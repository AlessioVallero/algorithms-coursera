import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The SocialNetworkConnectivity class model.
 *
 * @author Alessio Vallero
 */
public class SocialNetworkConnectivity {
    private final WeightedQuickUnionUF weightedQuickUnionTimestamps;

    /**
     * Creates an n-sized array from file, each element representing a person
     *
     *  @param n Size of the array
     */
    public SocialNetworkConnectivity(int n) {
        weightedQuickUnionTimestamps = new WeightedQuickUnionUF(n);
    }

    /**
     * Read a sorted log file of established friendships and return the earliest time
     * when everybody is connected, even through others.
     * If there is no complete connection, returns an empty string.
     * @param filename Log file containing timestamps of friendships
     * @return Earliest time when everybody is connected, empty string otherwise.
     */
    public String earliestCompleteConnection(String filename) {
        String output = "";
        try {
            File myFile = new File(filename);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String[] lineElements = myReader.nextLine().split(" ");

                weightedQuickUnionTimestamps.union(Integer.parseInt(lineElements[1]), Integer.parseInt(lineElements[2]));

                if (weightedQuickUnionTimestamps.count() == 1) {
                    output = lineElements[0];

                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return output;
    }

    /**
     * Test client
     *
     * @param args Proper Usage is: SocialNetworkConnectivity <ARRAY_SIZE> <TRIALS>
     */
    public static void main(String[] args) {
        // Check how many arguments were passed in
        if (args.length < 3) {
            System.out.println("Proper Usage is: SocialNetworkConnectivity <ARRAY_SIZE> <DENSITY> <TRIALS>");
        }
        else {
            final int arraySize = Integer.parseInt(args[0]);
            final int densityOfFriendships = Integer.parseInt(args[1]);
            final int trials = Integer.parseInt(args[2]);
            final String filename = "social.txt";

            Stopwatch stopwatch = new Stopwatch();

            // For each trial, we create a new array and create a file containing frienships <TIMESTAMPMS PERSON1 PERSON2>
            for (int i = 0; i < trials; i++) {
                try {
                    Set<String> connectedPeople = new HashSet<>();

                    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                    for (int j = 0; j < arraySize * densityOfFriendships; j++) {
                        // Generate random people IDs
                        // - randomPerson1 cannot be equals to randomPerson2
                        // - Connection between two people cannot be done more than once (using helper HashSet to track)
                        // - We stop generating new IDs if we've connected N different people to avoid infinite loops of regenerations
                        int randomPerson1 = - 1;
                        int randomPerson2 = -1;
                        do {
                            randomPerson1 = StdRandom.uniform(0, arraySize);
                            randomPerson2 = StdRandom.uniform(0, arraySize);
                        }
                        while( connectedPeople.size() < arraySize * 2 &&
                               ( randomPerson1 == randomPerson2 ||
                                 connectedPeople.contains(randomPerson1 + " " + randomPerson2) ||
                                 connectedPeople.contains(randomPerson2 + " " + randomPerson1) ) );

                        // <RANDOM_TIMESTAMP_MS> <ID_PERSON1> <ID_PERSON2>
                        String newLine = (1577548293575L + (j + 1) * 1000 ) + " " + randomPerson1 + " " + randomPerson2;
                        writer.write(newLine);
                        writer.newLine();

                        // Remove to print created line
                        //System.out.println(newLine);

                        connectedPeople.add(randomPerson1 + " " + randomPerson2);
                        connectedPeople.add(randomPerson2 + " " + randomPerson1);
                    }
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                SocialNetworkConnectivity socialNetworkConnectivity = new SocialNetworkConnectivity(arraySize);
                
                String earliestCompleteConnection = socialNetworkConnectivity.earliestCompleteConnection(filename);
                if (earliestCompleteConnection.isEmpty()) {
                    System.out.println( "No complete connection detected in file " + filename );
                }
                else {
                    System.out.println("Earliest complete friendships time: " + earliestCompleteConnection);
                }
            }

            System.out.println("elapsed time: " + stopwatch.elapsedTime());
        }
    }
}
