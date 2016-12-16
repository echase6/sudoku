package com.example.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eric on 11/17/2016.
 * <p>
 * Board related functions.
 */
public class Board {
        private static Integer order;
        public static Integer size;
        private List<List<List<Cell>>> cells;
        private HashMap<Integer, String> charMap = new HashMap<>();
        private final String order2map;
        private final String order3map;
        private final String order4map;


    public Board(Integer order) {
        this.order = order;
        order2map = "1234";
        order3map = "123456789";
        order4map = "0123456789ABCDEF";
        charMap.put(2, order2map);
        charMap.put(3, order3map);
        charMap.put(4, order4map);
    }


    public void makeBoard(Boolean addDummyData) {
        this.size = order * order;

//        this.cells = new Boolean[size][size][size];
        this.cells = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            ArrayList<List<Cell>> cols = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                int box =  j / order + (i / order) * order;
                ArrayList<Cell> nums = new ArrayList<>(size);
                for (int k = 0; k < size; k++) {
                    Cell cell;
                    if (addDummyData) {
                        cell = new Cell(i, j, k, box, true);
                    } else {
                        cell = new Cell(i, j, k, box, true);
                    }
                    nums.add(cell);
                }
                cols.add(nums);
            }
            cells.add(cols);
        }
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
                        line.append(' ').append(getChar(cells.get(i * order + j).get(k * order + l))).append(' ');
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
                    if (cells.get(i).get(j).get(k).getFilled()) {  // Don't let this freak you out...
                        line.append(charMap.get(order).charAt(j));
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
        Integer index;
        String letterSet = charMap.get(order);
        try {
            String line = bReader.readLine();
            while (line != null) {
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) != '.') {
                        index = letterSet.indexOf(line.substring(j, j+1));
                        for (int k = 0; k < size; k++) {
                            cells.get(i).get(j).get(k).setFilled(false);
                        }
                        cells.get(i).get(j).get(index).setFilled(true);
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
    private Character getChar(List<Cell> shaft) {
        Character value = null;
        for (int i = 0; i < shaft.size(); i++) {
            if (shaft.get(i).getFilled()) {
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
    private Integer countShaftChoices(List<Cell> shaft) {
        Integer count = 0;
        for (int i = 0; i < shaft.size(); i++) {
            if (shaft.get(i).getFilled()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return the number of cells that are filled to particular quantity.
     *
     * @param qty   the quantity to test to.
     * @return
     */
    private Integer countFilledCellsToQty(Board board, Integer qty) {
        Integer count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (countShaftChoices(cells.get(i).get(j)) == qty) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Return the number of cells that have only one cell filled.
     *
     * @return
     */
    public Integer countFilledCells(Board board) {
        return countFilledCellsToQty(board, 1);
    }

    /**
     * Returns the number of cells with a true in them.
     * @return
     */
    public Integer sumFilledCells(Board board)
    {
        Integer count = 0;
        List<List<List<Cell>>> cells = board.getCells();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (cells.get(i).get(j).get(k).getFilled())
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static Integer getSize()
    {
        return size;
    }

    public static Integer getOrder()
    {
        return order;
    }

    public List<List<List<Cell>>> getCells()
    {
        return cells;
    }

//    public Boolean[][][] getCells()
//    {
//        return cells;
//    }
}
