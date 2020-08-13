/* *****************************************************************************
 *  Name: JWang925
 *  Date: 2020-Jul-27
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    private boolean goalFound = false;
    private BoardWithPriority goalBoardWithPriority;
    private int moves;
    private boolean isSolvable;
    private ArrayList<Board> solution = new ArrayList<Board>();

    private class BoardWithPriority implements Comparable<BoardWithPriority> {
        private int priority;
        private Board board;
        private int moves;
        private BoardWithPriority previousBoard; // points to previous board


        // Constructor
        public BoardWithPriority(Board board, int moves, BoardWithPriority previousBoard) {
            this.board = board;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
            this.previousBoard = previousBoard;
        }

        public int compareTo(BoardWithPriority that) {
            if (this.priority < that.priority)
                return -1;
            else if (this.priority > that.priority)
                return +1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        BoardWithPriority initialWithPriority = new BoardWithPriority(initial, 0, null);
        MinPQ<BoardWithPriority> searchTree = new MinPQ<BoardWithPriority>();
        searchTree.insert(initialWithPriority);

        BoardWithPriority initialWithPriorityTwin = new BoardWithPriority(initial.twin(), 0, null);
        MinPQ<BoardWithPriority> searchTreeTwin = new MinPQ<BoardWithPriority>();
        searchTreeTwin.insert(initialWithPriorityTwin);

        // Search each search tree once and check if goal is reached
        while (!goalFound) {
            popAndInsert(searchTree);
            popAndInsertTwin(searchTreeTwin);
        }

        //
        if (!isSolvable)
            solution = null;
        else {
            while (goalBoardWithPriority != null) {
                solution.add(goalBoardWithPriority.board);
                goalBoardWithPriority = goalBoardWithPriority.previousBoard;
            }
            Collections.reverse(solution);
        }
    }

    private void popAndInsert(MinPQ<BoardWithPriority> searchTree) {
        BoardWithPriority temp = searchTree.delMin();

        // Debug output below
        // System.out.println("popping the following from search tree");
        // System.out.println(temp.board);

        if (temp.board.isGoal()) {
            goalBoardWithPriority = temp;
            goalFound = true;
            moves = temp.priority;
            isSolvable = true;
        }
        for (Board i : temp.board.neighbors()) {
            if (temp.previousBoard != null)
                if (i.equals(temp.previousBoard.board))
                    continue;
            searchTree.insert(new BoardWithPriority(i, temp.moves + 1, temp));

        }
    }

    private void popAndInsertTwin(MinPQ<BoardWithPriority> searchTree) {
        BoardWithPriority temp = searchTree.delMin();

        // Debug output below
        // System.out.println("popping the following from twin search tree");
        // System.out.println(temp.board);

        if (temp.board.isGoal()) {
            goalFound = true;
            moves = -1;
            isSolvable = false;
        }
        for (Board i : temp.board.neighbors())
            searchTree.insert(new BoardWithPriority(i, temp.moves + 1, temp));
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
        // Create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);
        System.out.println(solver.moves());
        System.out.println(solver.solution());

    }
}
