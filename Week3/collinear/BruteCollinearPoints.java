/* *****************************************************************************
 *  Name: JWang925
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSeg = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points.length == 0)
            throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        // sort the incoming points first
        Arrays.sort(points);
        // System.out.println(points[0].toString());

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[i + 1]))
                throw new IllegalArgumentException();
        }

        // brute force: loop through all possibilities
        for (int i = 0; i < points.length - 3; i++)
            for (int j = i + 1; j < points.length - 2; j++)
                for (int k = j + 1; k < points.length - 1; k++)
                    for (int m = k + 1; m < points.length; m++)
                        if (points[i].slopeTo(points[j])
                                == (points[j].slopeTo(points[k]))
                                && points[j].slopeTo(points[k])
                                == points[k].slopeTo(points[m])) {
                            lineSeg.add(new LineSegment(points[i], points[m]));
                        }


    }

    public int numberOfSegments() {
        return lineSeg.size();
    }

    public LineSegment[] segments() {
        return lineSeg.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        /*
        Point[] pts = new Point[5];
        pts[0] = new Point(3, 3);
        pts[1] = new Point(2, 3);
        pts[2] = new Point(4, 3);
        pts[3] = new Point(5, 3);
        pts[4] = new Point(10, 3);
        BruteCollinearPoints test = new BruteCollinearPoints(pts);
        System.out.print(test.numberOfSegments());
        */

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
            if (segment == null)
                continue;
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
