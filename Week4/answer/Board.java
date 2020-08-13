/* *****************************************************************************
 *  Name: JWang925
 *  Date: 2020-Jul-27
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public final class Board {

    private final int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("constr arg cannot be null");
        board = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles.length; j++)
                board[i][j] = tiles[i][j];

    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuf = new StringBuilder();
        stringBuf.append(board.length + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                stringBuf.append(board[i][j]);
                stringBuf.append(" ");
            }
            stringBuf.append("\n");
        }
        return stringBuf.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int retValue = 0;
        int correctValue = 1;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++, correctValue++) {
                if (board[i][j] == 0)
                    continue;
                if (board[i][j] != correctValue)
                    retValue++;

            }
        return retValue;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int retValue = 0;
        // int correctValue = 1;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++ /*, correctValue++ */) {
                if (board[i][j] == 0)
                    continue;

                // Horizontal distance
                retValue += Math.abs(j - (board[i][j] - 1) % board.length);
                // Vertical distance
                retValue += Math.abs(i - (board[i][j] - 1) / board.length);
            }
        return retValue;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        // Cast y to type Board now.
        Board that = (Board) y;

        if (this.dimension() != that.dimension())
            return false;

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (this.board[i][j] != that.board[i][j])
                    return false;

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int[] zeroPos = findZero();
        int numNeighbors = 4;

        int[][] boardCopy = new int[board.length][board.length];

        // Get left Neighbors
        copyArray(this.board, boardCopy, board.length);
        assert zeroPos != null;
        if (zeroPos[0] != 0) {
            boardCopy[zeroPos[0]][zeroPos[1]] = boardCopy[zeroPos[0] - 1][zeroPos[1]];
            boardCopy[zeroPos[0] - 1][zeroPos[1]] = 0;
            neighbors.add(new Board(boardCopy));
        }

        // Get right Neighbors
        copyArray(this.board, boardCopy, board.length);
        if (zeroPos[0] != board.length - 1) {
            boardCopy[zeroPos[0]][zeroPos[1]] = boardCopy[zeroPos[0] + 1][zeroPos[1]];
            boardCopy[zeroPos[0] + 1][zeroPos[1]] = 0;
            neighbors.add(new Board(boardCopy));
        }

        // Get top Neighbors
        copyArray(this.board, boardCopy, board.length);
        if (zeroPos[1] != 0) {
            boardCopy[zeroPos[0]][zeroPos[1]] = boardCopy[zeroPos[0]][zeroPos[1] - 1];
            boardCopy[zeroPos[0]][zeroPos[1] - 1] = 0;
            neighbors.add(new Board(boardCopy));
        }

        // Get bottom Neighbors
        copyArray(this.board, boardCopy, board.length);
        if (zeroPos[1] != board.length - 1) {
            boardCopy[zeroPos[0]][zeroPos[1]] = boardCopy[zeroPos[0]][zeroPos[1] + 1];
            boardCopy[zeroPos[0]][zeroPos[1] + 1] = 0;
            neighbors.add(new Board(boardCopy));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] boardCopy; // Stores the twin board
        int i = 0;
        int j = 0;
        int i2 = 0;
        int j2 = 0;

        while (((i == i2 && j == j2) || (board[i][j] == 0 || board[i2][j2] == 0))) {
            // i = StdRandom.uniform(0, board.length);
            // j = StdRandom.uniform(0, board.length);
            // i2 = StdRandom.uniform(0, board.length);
            // j2 = StdRandom.uniform(0, board.length);
            i++;
            i = i % board.length;
            j = i / board.length;
            i2 = board.length - 1 - i;
            j2 = board.length - 1 - j;
        }
        boardCopy = new int[board.length][board.length];
        copyArray(board, boardCopy, board.length);
        int temp = boardCopy[i2][j2];
        boardCopy[i2][j2] = boardCopy[i][j];
        boardCopy[i][j] = temp;

        return new Board(boardCopy);
    }


    // Return position of 0 in the board
    private int[] findZero() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] == 0)
                    return new int[] { i, j };
        return new int[] { -1, -1 };
    }

    private void copyArray(int[][] origin, int[][] destination, int size) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                destination[i][j] = origin[i][j];
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        System.out.println(initial.toString());
        System.out.println("Test hamming value");
        System.out.println(initial.hamming());
        System.out.println("Test manhattan value");
        System.out.println(initial.manhattan());
        System.out.println("get neighbors");
        System.out.println(initial.neighbors());
        System.out.println("get twins");
        System.out.println("twin #1");
        System.out.println(initial.twin());
        System.out.println("twin #2");
        System.out.println(initial.twin());
        System.out.println("Test isGoal");
        System.out.println(initial.isGoal());


    }


}
