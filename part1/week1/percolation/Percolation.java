import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * The Percolation class models of a percolation system.
 *
 * @author Alessio Vallero
 */
public class Percolation {
    private final int virtualTop;
    private final int virtualBottom;

    private final int gridDiameter;
    private int openCount;
    private boolean[] openStatuses;

    private final WeightedQuickUnionUF weightedQuickUnionUFIsFull;
    private final WeightedQuickUnionUF weightedQuickUnionUFPercolation;

    /**
     * Creates n-by-n grid, with all sites initially blocked and two WeightedQuickUnionUF to avoid
     * backwash problems due to the connection of virtual top to the virtual bottom
     *
     * @param n diameter of the n*n grid
     */
    public Percolation(int n) {
        validatePercInput(n);

        gridDiameter = n;

        virtualTop = 0;
        virtualBottom = n * n + 1;

        weightedQuickUnionUFIsFull = new WeightedQuickUnionUF(n * n + 1);
        weightedQuickUnionUFPercolation = new WeightedQuickUnionUF(n * n + 2);

        // Init "open" statutes array
        openStatuses = new boolean[n * n + 2];
        for (int i = 1; i < n * n + 2; i++) {
            openStatuses[i] = false;
        }
    }

    /**
     * Throw an IllegalArgumentException in the constructor if n ≤ 0.
     *
     * @param n Size of the grid
     */
    private void validatePercInput(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
    }

    /**
     * Throw an IllegalArgumentException if any argument to open(), isOpen(), or isFull() is outside
     * its prescribed range.
     */
    private void validateRowCol(int row, int col) {
        if (row < 1 || col < 1 || row > gridDiameter || col > gridDiameter)
            throw new IllegalArgumentException();
    }

    /**
     * Convert row, col indexes to the correct index for a 1D array representing a 2D matrix. Row,
     * col validation must be done by the caller.
     *
     * @param row Row number, greater than 0
     * @param col Col number, greater than 0
     * @return Index of row, col in the 1D array
     */
    private int rowColTo1D(int row, int col) {
        return (row - 1) * gridDiameter + (col - 1) + 1;
    }

    /**
     * Opens the site (row, col) if it is not open already. Each site is united with adjacent sites,
     * if they are open. First row sites are connected to virtual top and last row sites to virtual
     * bottom, to avoid n^2 search in the percolates function.
     *
     * @param row Row number, greater than 0
     * @param col Col number, greater than 0
     */
    public void open(int row, int col) {
        validateRowCol(row, col);

        int index = rowColTo1D(row, col);

        // If previously opened, we do nothing
        if (!openStatuses[index]) {
            openStatuses[index] = true;
            openCount++;

            // First row site connected to virtual top
            if (row == 1) {
                weightedQuickUnionUFIsFull.union(index, virtualTop);
                weightedQuickUnionUFPercolation.union(index, virtualTop);
            }

            // Last row site connected to virtual bottom
            if (row == gridDiameter) {
                weightedQuickUnionUFPercolation.union(index, virtualBottom);
            }

            // Unite to adjacent top element, if open
            if (row > 1) {
                int nUp = rowColTo1D(row - 1, col);
                if (openStatuses[nUp]) {
                    weightedQuickUnionUFIsFull.union(index, nUp);
                    weightedQuickUnionUFPercolation.union(index, nUp);
                }
            }

            // Unite to adjacent bottom element, if open
            if (row < gridDiameter) {
                int nDown = rowColTo1D(row + 1, col);
                if (openStatuses[nDown]) {
                    weightedQuickUnionUFIsFull.union(index, nDown);
                    weightedQuickUnionUFPercolation.union(index, nDown);
                }
            }

            // Unite to adjacent left element, if open
            if (col > 1) {
                int nLeft = rowColTo1D(row, col - 1);
                if (openStatuses[nLeft]) {
                    weightedQuickUnionUFIsFull.union(index, nLeft);
                    weightedQuickUnionUFPercolation.union(index, nLeft);
                }
            }

            // Unite to adjacent right element, if open
            if (col < gridDiameter) {
                int nRight = rowColTo1D(row, col + 1);
                if (openStatuses[nRight]) {
                    weightedQuickUnionUFIsFull.union(index, nRight);
                    weightedQuickUnionUFPercolation.union(index, nRight);
                }
            }
        }
    }

    /**
     * Is site (row, col) currently open?
     *
     * @param row Row number, greater than 0
     * @param col Col number, greater than 0
     * @return true if site (row, col) is currently open, false otherwise.
     */
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);

        int index = rowColTo1D(row, col);

        return openStatuses[index];
    }

    /**
     * Is site (row, col) currently full? Check with IsFull WeightedQuickUnionUF object if site is
     * connected to the virtual top
     *
     * @param row Row number, greater than 0
     * @param col Col number, greater than 0
     * @return true if site (row, col) is currently open, false otherwise.
     */
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);

        int index = rowColTo1D(row, col);

        return openStatuses[index]
                && weightedQuickUnionUFIsFull.find(index) == weightedQuickUnionUFIsFull
                .find(virtualTop);
    }

    /**
     * Returns the number of open sites
     *
     * @return Current number of open sites
     */
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?

    /**
     * Does the system percolate? Check with Percolation WeightedQuickUnionUF object if virtual top
     * and virtual bottom are connected.
     *
     * @return true if the system percolate, false otherwise
     */
    public boolean percolates() {
        return weightedQuickUnionUFPercolation.find(virtualTop) == weightedQuickUnionUFPercolation
                .find(virtualBottom);
    }
}
