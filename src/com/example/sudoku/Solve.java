package com.example.sudoku;


/**
 * Created by Eric on 11/16/2016.
 *
 * Main function for the Sudoku puzzle solver.
 *
 * Args:  0 -- board order (2, 3, 4)  Note that board side dimension is order^2
 *        1 -- iter amount.  This trades off how hard the algorithm works vs. number of passes
 *                           Ultimately, the algorithm should select this, starting at 1 and
 *                           incrementing when stalled.
 *        2 -- the puzzle name to solve
 *
 * The solving algorithm comes from viewing the board as a 3-dimensional cube (i.e., number is depth)
 *   and each cell is either filled or not filled.  This means, when viewing the board in a 2D arrangement
 *   (traditional) if a cell has a number in it then only one cell in the 'depth' is true and the rest
 *   are false.
 *
 * The board is considered as a collection of slices, which are 2D, and then arrangements of rows (for example)
 *   are considered and (again, for example) if only one cell hold a true value then all the cells in the
 *   orthogonal (columns) need to be false.  Changing cells that are true to false is how progress is made
 *   in solving the puzzle.
 *
 * The logic presented above is extended to consider two-values-for-two rows, three-values-for-three rows, etc.
 *   The 'iter' value sets an upper limit to consider.  The valid range is between 1 and order^2 - 1, inclusive.
 *
 * The logic presented above is also extended by considering slices on each of the three 3D board faces, and
 *   additionally, by considering rows-first or columns first.  Boxes are a somewhat special case where a special
 *   iterator throughout the box is created and used.  All totaled, there are eight ways to represent the board.
 *
 * The above process is continued until either all cells have unique values (order^4 cells are filled) or no
 *   progress is made in unfilling the cells on successive passes.
 *
 */
public class Solve {

    public static void main(String[] args) {
        System.out.println("\033[?25l"); // Remove the cursor
        System.out.println("\033[2J");   // Clear the screen
        System.out.println("\033[0;0H"); // Move cursor to top of screen

        Integer order = Integer.parseInt(args[0]);
        Integer iterLimit = Integer.parseInt(args[1]);
        String puzzle = args[2];

        Board board = new Board(order, puzzle);
        board.makeBoard(false);
        board.loadBoard();
        board.displayBoard();

        Solver solver = new Solver(board, iterLimit);
        solver.run();

        System.out.println("\033[?25h"); // Restore the cursor
    }

}
