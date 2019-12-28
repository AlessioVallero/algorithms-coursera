import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * The Percolation class models of a percolation system.
 *
 * @author Alessio Vallero
 */
public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final double percolationThresholdsMean, percolationThresholdsStdDev,
            percolationThresholdsConfidenceLow, percolationThresholdsConfidenceHigh;

    /**
     * Perform independent trials on an n-by-n grid. Tilde running time: 1.25 * T * n^2
     *
     * @param n      Size of grid
     * @param trials Number of attemps
     */
    public PercolationStats(int n, int trials) {
        validateStatsInput(n, trials);

        double[] percolationThresholds = new double[trials];

        // For each trial, we create a new percolation object. We open sites until it percolates
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            do {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);

                percolation.open(randomRow, randomCol);

            } while (!percolation.percolates());

            // Compute threshold for this percolation
            percolationThresholds[i] = percolation.numberOfOpenSites() * 1.0 / (n * n);
        }

        // Mean
        percolationThresholdsMean = StdStats.mean(percolationThresholds);
        // Standard Deviation
        percolationThresholdsStdDev = StdStats.stddev(percolationThresholds);
        // Confidence Low
        percolationThresholdsConfidenceLow = mean() - (CONFIDENCE_95 * stddev()) / (Math
                .sqrt(trials));
        // Confidence High
        percolationThresholdsConfidenceHigh = mean() + (CONFIDENCE_95 * stddev()) / (Math
                .sqrt(trials));
    }

    /**
     * Throw an IllegalArgumentException in the constructor if either n ≤ 0 or trials ≤ 0.
     *
     * @param n      Size of the grid
     * @param trials Number of trials
     */
    private void validateStatsInput(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
    }

    /**
     * Sample mean of percolation threshold
     *
     * @return Previously computed percolation thresholds mean
     */
    public double mean() {
        return percolationThresholdsMean;
    }

    /**
     * Sample standard deviation of percolation threshold
     *
     * @return Previously computed percolation thresholds standard deviation
     */
    public double stddev() {
        return percolationThresholdsStdDev;
    }

    /**
     * Low endpoint of 95% confidence interval
     *
     * @return Previously computed low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return percolationThresholdsConfidenceLow;
    }

    /**
     * High endpoint of 95% confidence interval
     *
     * @return Previously computed high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return percolationThresholdsConfidenceHigh;
    }

    /**
     * Test client
     *
     * @param args Proper Usage is: PercolationStats <GRID_SIZE> <TRIALS_N>
     */
    public static void main(String[] args) {
        // Check how many arguments were passed in
        if (args.length < 2) {
            System.out.println("Proper Usage is: PercolationStats <GRID_SIZE> <TRIALS_N>");
        }
        else {
            Stopwatch stopwatch = new Stopwatch();
            PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]),
                                                                     Integer.parseInt(args[1]));
            System.out.println("elapsed time: " + stopwatch.elapsedTime());

            System.out.println("mean: " + percolationStats.mean());
            System.out.println("stddev: " + percolationStats.stddev());
            System.out.println("95% confidence interval: [" + percolationStats.confidenceLo() + ", "
                                       + percolationStats.confidenceHi() + "]");
        }
    }
}
