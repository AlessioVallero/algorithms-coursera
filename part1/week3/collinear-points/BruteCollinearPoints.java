import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The BruteCollinearPoints class model.
 *
 * @author Alessio Vallero
 */
public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    /**
     * Finds all line segments containing 4 points with a brute force algorithm. No duplicated or
     * null points are allowed.
     *
     * @param points Take an array of distinct point and create line segments, if any
     */
    public BruteCollinearPoints(Point[] points) {
        checkFormNullInput(points);

        Point[] clonedInput = points.clone();
        // Sort points to facilitate search of duplicated point and duplicated segments
        Arrays.sort(clonedInput);

        ArrayList<LineSegment> arLSPoints = new ArrayList<>();

        Point previousI = null;
        // If we have less than 4 points, we already know that we don't have 4 points line segments
        for (int i = 0; i < clonedInput.length; i++) {
            // Look for duplicates
            if (previousI != null && clonedInput[i].compareTo(previousI) == 0)
                throw new IllegalArgumentException();
            previousI = clonedInput[i];

            for (int j = i + 1; j < clonedInput.length - 2; j++) {
                for (int k = j + 1; k < clonedInput.length - 1; k++) {
                    // If the slopeOrder of the first 3 points is not equals, we don't need to search for a 4th point line segment with these 3 points
                    // so we can skip the z for loop
                    if (clonedInput[i].slopeOrder()
                                      .compare(clonedInput[j],
                                               clonedInput[k]) != 0) continue;

                    for (int z = k + 1; z < clonedInput.length; z++) {
                        // If the slopeOrder of j, k and z points is also equals, we've got a line segment
                        if (clonedInput[j].slopeOrder().compare(clonedInput[k], clonedInput[z])
                                == 0) {
                            arLSPoints.add(new LineSegment(clonedInput[i], clonedInput[z]));
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
