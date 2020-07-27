/* *****************************************************************************
 *  Name: JWang925
 *  Date: 2020-Jul-23
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private ArrayList<LineSegment> lineSeg = new ArrayList<LineSegment>(10);

    /*
    private ArrayList<LineRepresentation> lineRep = new ArrayList<LineRepresentation>(10);


    private class LineRepresentation implements Comparable<LineRepresentation> {
        private Point startingPt;
        private double slope;

        private LineRepresentation(Point startingPt, double slope) {
            this.startingPt = startingPt;
            this.slope = slope;
        }

        public int compareTo(LineRepresentation that) {
            if (slope < that.slope)
                return -1;
            else if (slope > that.slope)
                return 1;
            else
                return startingPt.compareTo(that.startingPt);
        }



    // private boolean equals(LineRepresentation that) {
    //    return slope == that.slope && startingPt.equals(that.startingPt);
    // }
    } */

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points.length == 0)
            throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        // sort the incoming points first, time cost O(n log n)
        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[i + 1]))
                throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            // create a copy of the array of points
            Point[] ptCopy = points.clone();

            // Java uses mergesort for Objects
            Arrays.sort(ptCopy, ptCopy[i].slopeOrder());

            // debug print
            // for (int ii = 0; ii < ptCopy.length; ii++)
            //    System.out.println(ptCopy[ii]);
            // System.out.println();

            // skip k = 0 because k = 0 points to the point[i] itself
            // slope is Double.NEGATIVE_INFINITY
            double slopeCandidate = Double.NEGATIVE_INFINITY;
            ArrayList<Point> linePts = new ArrayList<>(6);


            for (int k = 1; k < ptCopy.length; k++) {
                double slopeToOrigin = ptCopy[0].slopeTo(ptCopy[k]);
                // cases to record a line
                // 2. we have seen a different slope than last one and length was >=4
                if (linePts.size() >= 3 && slopeCandidate != slopeToOrigin) {
                    addLineSeg(linePts, ptCopy[0]);
                    linePts.clear();
                }

                if (slopeToOrigin == slopeCandidate) {
                    linePts.add(ptCopy[k]);
                }
                else {
                    slopeCandidate = slopeToOrigin;
                    linePts.clear();
                    linePts.add(ptCopy[k]);
                }
            }
            if (linePts.size() >= 3) {
                addLineSeg(linePts, ptCopy[0]);
                linePts.clear();
            }

        }
    }

    public int numberOfSegments() {
        return lineSeg.size();
    }        // the number of line segments

    public LineSegment[] segments() {                // the line segments
        return lineSeg.toArray(new LineSegment[0]);
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
            if (segment == null)
                continue;
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private void addLineSeg(ArrayList<Point> linePts, Point origin) {
        // convert arraylist of points to lineSegment
        // System.out.println("line points before sorting");
        // System.out.println(linePts);
        linePts.add(origin);
        Collections.sort(linePts);
        // System.out.println("line points after sorting");
        // System.out.println(linePts);
        if (!linePts.get(0).equals(origin))
            return;

        LineSegment lineSegToAdd = new LineSegment(linePts.get(0),
                                                   linePts.get(linePts.size() - 1));
        lineSeg.add(lineSegToAdd);
    }
}
