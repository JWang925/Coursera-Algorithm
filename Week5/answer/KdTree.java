/* *****************************************************************************
 *  Name: JWang925
 *  Date: 2020Aug11
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private KdNode root;
    private int numberOfPoints = 0;

    private class KdNode {
        private KdNode leftChild;
        private KdNode rightChild;
        private Point2D point;
        private int d;  // d could be 0, x, divide space by x axis or
        // or 1, divide space by y axis

        public KdNode(Point2D pt, int degree) {
            this.d = degree;
            this.point = pt;
        }
    }


    public KdTree() {  // construct an empty set of points
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return numberOfPoints;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D pt) {
        if (pt == null) {
            throw new IllegalArgumentException("cannot insert null pt");
        }

        insertSubTree(root, pt);
    }


    private void insertSubTree(KdNode kdnode, Point2D pt) {

        if (size() == 0) {
            root = new KdNode(pt, 0);
            numberOfPoints++;
            return;
        }

        if (kdnode.d == 0) {
            if (kdnode.point.x() > pt.x()) {
                if (kdnode.leftChild == null) {
                    kdnode.leftChild = new KdNode(pt, 1 - kdnode.d);
                    numberOfPoints++;
                }
                else {
                    insertSubTree(kdnode.leftChild, pt);
                }
            }
            else {
                if (kdnode.rightChild == null) {
                    kdnode.rightChild = new KdNode(pt, 1 - kdnode.d);
                    numberOfPoints++;
                }
                else {
                    insertSubTree(kdnode.rightChild, pt);
                }
            }
        }
        else {
            if (kdnode.point.y() > pt.y()) {
                if (kdnode.leftChild == null) {
                    kdnode.leftChild = new KdNode(pt, 1 - kdnode.d);
                    numberOfPoints++;
                }
                else {
                    insertSubTree(kdnode.leftChild, pt);
                }
            }
            else {
                if (kdnode.rightChild == null) {
                    kdnode.rightChild = new KdNode(pt, 1 - kdnode.d);
                    numberOfPoints++;
                }
                else {
                    insertSubTree(kdnode.rightChild, pt);
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("cannot query null pt");
        }

        return true;
    }

    // check if point is in a subtree
    private boolean containsSubTree(KdNode kdnode, Point2D pt) {
        if (kdnode == null)
            return false;
        if (kdnode.point.equals(pt))
            return true;
        if (kdnode.d == 0) {
            if (pt.x() < kdnode.point.x()) {
                return containsSubTree(kdnode.leftChild, pt);
            }
            else {
                return containsSubTree(kdnode.rightChild, pt);
            }
        }
        else { // degree is 1.
            if (pt.y() < kdnode.point.y()) {
                return containsSubTree(kdnode.leftChild, pt);
            }
            else {
                return containsSubTree(kdnode.rightChild, pt);
            }
        }
    }

    public void draw() {               // draw all points to standard draw
        drawSubTree(root);
    }

    private void drawSubTree(KdNode kdnode) {
        if (kdnode.point != null)
            kdnode.point.draw();
        if (kdnode.leftChild != null) {
            drawSubTree(kdnode.leftChild);
        }
        if (kdnode.rightChild != null) {
            drawSubTree(kdnode.rightChild);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> returnArray = new ArrayList<Point2D>();
        if (rect == null) {
            throw new IllegalArgumentException("rect input is null");
        }
        if (root == null) {
            return returnArray;
        }
        transverseNode(rect, root, returnArray);

        return returnArray;
    }

    private void transverseNode(RectHV rect, KdNode kdnode, ArrayList<Point2D> returnArray) {
        if (kdnode == null)
            return;
        if (rect.contains(kdnode.point)) {
            returnArray.add(kdnode.point);
        }
        if (kdnode.d == 0) {
            if (kdnode.point.x() >= rect.xmin()) // Should we go to left of tree?
                transverseNode(rect, kdnode.leftChild, returnArray);
            if (kdnode.point.x() <= rect.xmax()) // Should we go to the right?
                transverseNode(rect, kdnode.rightChild, returnArray);
        }

        if (kdnode.d == 1) {
            if (kdnode.point.y() >= rect.ymin()) // Should we go to left of tree?
                transverseNode(rect, kdnode.leftChild, returnArray);
            if (kdnode.point.y() <= rect.ymax()) // Should we go to the right?
                transverseNode(rect, kdnode.rightChild, returnArray);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        double minDistance = Double.POSITIVE_INFINITY;
        KdNode nearest = nearest(p, root, root);


        return nearest.point;

    }


    private KdNode nearest(Point2D p, KdNode kdnode, KdNode currentClosest) {


        // If we reach leaf of tree, return currentClosest
        if (kdnode == null)
            return currentClosest;

        // When we reach down to a node, we compare against current lowest
        if (kdnode.point.distanceTo(p) < currentClosest.point.distanceTo(p)) {
            currentClosest = kdnode;
        }

        // Then continue to check left/right children
        // Need to figure out if left or right is worth checking
        boolean checkLeft = false;
        boolean checkRight = false;

        if (kdnode.d == 0) {
            // Check if point is on the left/right tree
            if (p.x() < kdnode.point.x()) {
                checkLeft = true;
                // Then we decide if we need to look at the other tree
                // rightMin is the closest pt that potentially could exist on the right
                double rightMin = kdnode.point.x() - p.x();
                if (rightMin < currentClosest.point.distanceTo(p)) {
                    checkRight = true;
                }
            }
            else {
                checkRight = true;
                // Then we decide if we need to look at the other tree
                // leftMin is the closest pt that potentially could exist on the left
                double leftMin = p.x() - kdnode.point.x();
                if (leftMin < currentClosest.point.distanceTo(p)) {
                    checkLeft = true;
                }
            }
        }
        else {
            // Check if point is on the left/right tree
            if (p.y() < kdnode.point.y()) {
                checkLeft = true;
                // Then we decide if we need to look at the other tree
                // rightMin is the closest pt that potentially could exist on the right
                double rightMin = kdnode.point.y() - p.y();
                if (rightMin < currentClosest.point.distanceTo(p)) {
                    checkRight = true;
                }
            }
            else {
                checkRight = true;
                // Then we decide if we need to look at the other tree
                // leftMin is the closest pt that potentially could exist on the left
                double leftMin = p.y() - kdnode.point.y();
                if (leftMin < currentClosest.point.distanceTo(p)) {
                    checkLeft = true;
                }
            }
        }

        if (checkLeft)
            currentClosest = nearest(p, kdnode.leftChild, currentClosest);
        if (checkRight)
            currentClosest = nearest(p, kdnode.rightChild, currentClosest);

        return currentClosest;
    }


    private KdNode returnMin(KdNode node1, KdNode node2, Point2D target) {
        if (node1 == null && node2 == null)
            return null;
        if (node1 == null)
            return node2;
        if (node2 == null)
            return node1;

        double dist1 = target.distanceTo(node1.point);
        double dist2 = target.distanceTo(node2.point);
        if (dist1 < dist2)
            return node1;
        else
            return node2;
    }

    public static void main(String[] args) {
        KdTree testTree = new KdTree();
        testTree.insert(new Point2D(2, 2));
        testTree.insert(new Point2D(4, 3));
        testTree.insert(new Point2D(9, 8));
        testTree.insert(new Point2D(6, 3));
        testTree.insert(new Point2D(11, 3));
        testTree.insert(new Point2D(299, 333));
        testTree.insert(new Point2D(-55, 383));
        System.out.println(testTree.size());
        testTree.draw();
        System.out.println(testTree.contains(new Point2D(299, 333)));

        RectHV rect = new RectHV(100, 120, 300, 400);
        System.out.println(testTree.range(rect));

        System.out.println("nearest");

        System.out.println(testTree.nearest(new Point2D(2, 2.01)));


    }
}
