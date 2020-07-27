/* *****************************************************************************
 *  Name: JWang925
 *  Date: 2020Jul12
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> data = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            data.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(data.dequeue());
        }
    }
}
