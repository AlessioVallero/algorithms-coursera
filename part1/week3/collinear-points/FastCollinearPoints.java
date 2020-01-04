import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * The BruteCollinearPoints class model.
 *
 * @author Alessio Vallero
 */
public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    /**
     * Finds all line segments containing 4 points with a faster, sorting based algorithm. No
     * duplicated or null points are allowed.
     *
     * @param points Take an array of distinct point and create line segments, if any
     */
    public FastCollinearPoints(Point[] points) {
        checkFormNullInput(points);

        Point[] clonedInput = points.clone();
        // Sort points to facilitate search of duplicated point and duplicated segments
        Arrays.sort(clonedInput);

        ArrayList<LineSegment> arLSPoints = new ArrayList<>();

        Point previousI = null;
        for (int i = 0; i < clonedInput.length; i++) {
            Point pointP = clonedInput[i];

            // Look for duplicates
            if (previousI != null && pointP.compareTo(previousI) == 0)
                throw new IllegalArgumentException();
            previousI = pointP;

            // Copy array (point i excluded)
            Point[] arPointsToProcess = clonedInput.clone();

            Comparator<Point> comparatorWithP = pointP.slopeOrder();
            // Sort values by slopeOrder with Point p
            Arrays.sort(arPointsToProcess, comparatorWithP);

            // Create LinkedList of collinear points, inited with the first element
            LinkedList<Point> collinearPointsList = new LinkedList<>();
            collinearPointsList.add(arPointsToProcess[0]);
            // First slope between P and first element
            double previousSlope = pointP.slopeTo(arPointsToProcess[0]);

            // If collinearCount is not 1, we might be over the loop but with an ongoing collinear computation
            for (int j = 1; j < arPointsToProcess.length || collinearPointsList.size() > 1; j++) {
                // If subsequent elements have same slope with i, then they are part of a segment
                // And they are aded to the collinear LinkedList
                if (j < arPointsToProcess.length && Double.compare(previousSlope, pointP
                        .slopeTo(arPointsToProcess[j])) == 0) {
                    collinearPointsList.add(arPointsToProcess[j]);
                }
                else {
                    // If we have 4 or more collinear connections and the head of the list is the smallest point,
                    // then we are sure that this is not a subsegment and we can add the LineSegment
                    if (collinearPointsList.size() >= 3
                            && pointP.compareTo(collinearPointsList.peek()) < 0) {
                        arLSPoints.add(new LineSegment(pointP, arPointsToProcess[j - 1]));
                    }
                    // Reset LinkedList for attempting other LineSegments
                    collinearPointsList.clear();
                    if (j < arPointsToProcess.length) {
                        // If loop is not over, add point j as initial element
                        collinearPointsList.add(arPointsToProcess[j]);
                    }
                }

                if (j < arPointsToProcess.length)
                    previousSlope = pointP.slopeTo(arPointsToProcess[j]);
            }
        }

        this.lineSegments = arLSPoints.toArray(new LineSegment[0]);
    }

    /**
     * Validate input from null values and throws and IllegalArgumentException if needed
     *
     * @param points The input to validate
     */
    private void checkFormNullInput(Point[] points) {
        // No null inputs are allowed
        if (points == null) throw new IllegalArgumentException();

        for (Point p : points) {
            // No null elements are allowed
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * The number of line segments
     *
     * @return Number of line segments
     */
    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    /**
     * The line segments
     *
     * @return Array of line segments
     */
    public LineSegment[] segments() {
        return this.lineSegments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
