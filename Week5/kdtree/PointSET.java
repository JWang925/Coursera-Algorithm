/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> pointsSet = new SET<Point2D>();
    // API of datatype above https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/SET.html
    // data needs the key to have Comparable interface

    public PointSET() {  // construct an empty set of points

    }

    // is the set empty?
    public boolean isEmpty() {
        return pointsSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointsSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("cannot insert null pt");
        }
        pointsSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("cannot query null pt");
        }
        return pointsSet.contains(p);

    }

    public void draw() {               // draw all points to standard draw
        for (Point2D i : pointsSet)
            i.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> returnArray = new ArrayList<Point2D>();
        if (rect == null) {
            throw new IllegalArgumentException("rect input is null");
        }
        for (Point2D i : pointsSet) {
            if (rect.contains(i))
                returnArray.add(i);
        }
        return returnArray;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D i : pointsSet) {
            if (i.distanceTo(p) < minDistance) {
                nearest = i;
                minDistance = i.distanceTo(p);
            }
        }
        return nearest;

    }

    public static void main(String[] args) {
        PointSET testTree = new PointSET();
        testTree.insert(new Point2D(2, 2));
        testTree.insert(new Point2D(2, 3));
        System.out.println(testTree.size());
        testTree.draw();
    }
}
