package com.example.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Eric on 11/17/2016.
 * <p>
 * Board related functions.
 */
public class Board {
        private Integer order;
        private Integer size;
        private Boolean[][][] cells;
        private HashMap charMap;

    /**
     * makeBoard populates a board with dummy information
     *
     */
    public void init() {
        final String order2map = "0123";
        final String order3map = "123456789";
        final String order4map = "0123456789ABCDEF";
        HashMap<Integer, String> charMap = new HashMap<>();
        charMap.put(2, order2map);
        charMap.put(3, order3map);
        charMap.put(4, order4map);
    }

    public void makeBoard(Integer order, Boolean addDummyData) {
        this.order = order;
        this.size = order * order;
        this.cells = new Boolean[size][size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (addDummyData) {
                        cells[i][j][k] = (i == j && j == k);
                    } else {
                        cells[i][j][k] = true;
                    }
                }
            }
        }
        System.out.println("order = " + order);
    }

    /**
     * displayBoard does the obvious.
     */
    public void displayBoard() {
        StringBuilder border = new StringBuilder("+");
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                border.append("---");
            }
            border.append('+');
        }
        System.out.println(border);
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                StringBuilder line = new StringBuilder("|");
                for (int k = 0; k < order; k++) {
                    for (int l = 0; l < order; l++) {
                        line.append(' ').append(getChar(cells[i * order + j][k * order + l])).append(' ');
                    }
                    line.append('|');
                }
                System.out.println(line);
            }
            System.out.println(border);
        }
    }

    public void display3dBoard() {
        StringBuilder border = new StringBuilder("");
        for (int i = 0; i < size; i++) {
            border.append("+-");
            for (int j = 0; j < size; j++) {
                border.append("--");
            }
            border.append("+ ");
        }
        System.out.println(border);
        for (int i = 0; i < size; i++) {
            StringBuilder line = new StringBuilder("");
            for (int j = 0; j < size; j++) {
                line.append("| ");
                for (int k = 0; k < size; k++) {
                    if (cells[i][j][k]) {
                        line.append(k);
                    } else {
                        line.append('.');
                    }
                    line.append(' ');
                }
                line.append("| ");
            }
            System.out.println(line);
        }
        System.out.println(border);
    }

    public void loadBoard() {
        String directory = "puzzles/order_" + order;
        String filename = "sudoku_1.txt";
        String fullFileName = directory + '/' + filename;
        System.out.println(directory + "/" + filename);

        FileReader fReader = null;
        try {
            fReader = new FileReader(fullFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader bReader = new BufferedReader(fReader);

        Integer i = 0;
        try {
            String line = bReader.readLine();
            while (line != null) {
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) != '.') {
                        int gotChar = Integer.parseInt(line.substring(j, j + 1));
                        for (int k = 0; k < size; k++) {
                            cells[i][j][k] = false;
                        }
                        cells[i][j][gotChar - 1] = true; // TODO: make this a character look-up
//                        System.out.println("i: " + i + " j: " + j + " gotChar = " + gotChar);
                    }
                }

                i++;
                line = bReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
//                    value = charMap.;
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

    /**
     * Return the number of filled (true) cells in a shaft.
     *
     * @param shaft is a 1-dimensional array
     * @return character
     */
    private Integer countShaftChoices(Boolean[] shaft) {
        Integer count = 0;
        for (int i = 0; i < shaft.length; i++) {
            if (shaft[i]) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return the number of cells that are filled to particular quantity.
     *
     * @param cells
     * @param qty   the quantity to test to.
     * @return
     */
    private Integer countFilledCellsToQty(Boolean cells[][][], Integer qty) {
        Integer count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (countShaftChoices(cells[i][j]) == qty) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Return the number of cells that have only one cell filled.
     *
     * @param cells
     * @return
     */
    private Integer countChoicesLeft(Boolean cells[][][]) {
        return countFilledCellsToQty(cells, 1);
    }
}
