/* *****************************************************************************
 *  Name:              JWang925
 *  Last modified:     2020-Jul-01
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] site;

    private int nOpenSite = 0;

    private int edgeLength = 0;

    private int nNodes = 0;

    // Weighted quick-union data for full site
    private WeightedQuickUnionUF fullSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        edgeLength = n;

        site = new boolean[n][n];
        for (int row = 1; row <= n; row++)
            for (int col = 1; col <= n; col++)
                site[row - 1][col - 1] = false;

        // one additional node above top row (origin)
        // and one below bottom row (destination)
        nNodes = n * n + 1 + 1;

        // initialize the data structure for Quick Union
        fullSite = new WeightedQuickUnionUF(nNodes);

        // System.out.println(n + " by " + n + " site allocated");
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // if site is already open, do nothing
        if (isOpen(row, col))
            return;

        site[row - 1][col - 1] = true;
        nOpenSite++;

        // check connectivity with node on top
        if (row == 1)
            fullSite.union(0, convertToOneDim(1, col));
        else if (isOpen(row - 1, col))
            fullSite.union(convertToOneDim(row, col),
                           convertToOneDim(row - 1, col));

        // check connectivity with node on bottom
        if (row == edgeLength)
            fullSite.union(convertToOneDim(row, col), nNodes - 1);
        else if (isOpen(row + 1, col))
            fullSite.union(convertToOneDim(row, col),
                           convertToOneDim(row + 1, col));

        // check connectivity with node on left
        if (col != 1)
            if (isOpen(row, col - 1))
                fullSite.union(convertToOneDim(row, col),
                               convertToOneDim(row, col - 1));

        // check connectivity with node on right
        if (col != edgeLength)
            if (isOpen(row, col + 1))
                fullSite.union(convertToOneDim(row, col),
                               convertToOneDim(row, col + 1));

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return site[row - 1][col - 1];

    }

    // is the site (row, col) full?
    // A full site is an open site that can be connected to an open site
    // in the top row via a chain of neighboring (left, right, up, down)
    // open sites.
    public boolean isFull(int row, int col) {
        return fullSite.find(convertToOneDim(row, col))
                == fullSite.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return fullSite.find(0) == fullSite.find(nNodes - 1);
    }

    private void print() {
        for (int row = 0; row < edgeLength; row++) {
            for (int col = 0; col < edgeLength; col++)
                System.out.print((site[row][col]) ? "*" : "-");
            System.out.print("\n");
        }
    }

    private int convertToOneDim(int row, int col) {
        return (row - 1) * edgeLength + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation testCase = new Percolation(5);
        System.out.println(testCase.fullSite.count()); // return number of sets
        System.out.println("percolate?" + testCase.percolates());
        testCase.open(0, 0);
        System.out.println("percolate?" + testCase.percolates());
        testCase.open(1, 0);
        System.out.println("percolate?" + testCase.percolates());
        testCase.print();

    }
}
