/* *****************************************************************************
 *  Name:              JWang925
 *  Last modified:     2020-Jul-01
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int nTrial;

    private double[] threshold;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trails must both be > 0");
        nTrial = trials;
        threshold = new double[trials];

        for (int iTrail = 0; iTrail < nTrial; iTrail++) {
            Percolation test = new Percolation(n);

            // Open until percolates
            while (!test.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                test.open(row, col);
            }
            threshold[iTrail] = (double) test.numberOfOpenSites() / (n * n);

        }
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(nTrial));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(nTrial));
    }

    // test client (see below)
    public static void main(String[] args) {
        int gridLength = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats testRun = new PercolationStats(gridLength, trials);
        System.out.println("mean:" + testRun.mean());
        System.out.println("stddev:" + testRun.stddev());
        System.out.println("confidence interval: [" + testRun.confidenceLo() +
                                   "," + testRun.confidenceHi() + "]");

    }

}
