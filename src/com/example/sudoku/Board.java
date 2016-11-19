package com.example.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
            while (line != null)
            {
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) != '.')
                    {
                        int gotChar = Integer.parseInt(line.substring(j, j));
                        for (int k = 0; k < size; k++) {
                            cells[i][j][k] = false;
                        }
                        cells[i][j][gotChar] = true;
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
