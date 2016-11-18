package com.example.sudoku;

/**
 * Created by Eric on 11/17/2016.
 *
 * Board related functions.
 */
public class Board {
    public Integer order;
    public Integer size;
    public Boolean[][][] cells;

    /**
     * makeBoard populates a board with dummy information
     *
     * @param order is 2, 3 or 4
     */
    public void makeBoard(Integer order) {
        this.order = order;
        this.size = order * order;
        this.cells = new Boolean[size][size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    cells[i][j][k] = (i == j && j == k);
                }
            }
        }
        System.out.println("order = " + order);
    }

    /**
     * displayBoard does the obvious.
     */
    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(" " + getChar(cells[i][j]) + " ");
            }
            System.out.println("");
        }
    }

    public void loadBoard()
    {
        String directory = "puzzles/order_" + order;
        String filename = "sudoku_1.txt";
        System.out.println(directory + "/" + filename);
    }

    /**
     * solveBoard is the entry point for the solver.
     */
    public void solveBoard() {
        System.out.println("Solving board.");
    }

    /**
     * Return the character for an individual shaft.  Possibilities are:
     * -- a number, as long as there is one unique cell filled
     * -- a period, as long as there are more than one cell filled
     * -- an X, meaning that no cells are filled, which is an error state
     *
     * @param shaft is a 1-dimensional array
     * @return character
     */
    private Character getChar(Boolean[] shaft) {
        Character value = null;
        for (int i = 0; i < shaft.length; i++) {
            if (shaft[i]) {
                if (value != null) {
                    value = '.';
                } else {
                    value = Integer.toString(i + 1).charAt(0);
                }
            }
        }
        if (value == null) {
            return 'X';
        } else {
            return value;
        }
    }
}
