package com.example.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 11/17/2016.
 * <p>
 * Board related functions.
 */
public class Board {
        private Integer order;
        private Integer size;
        private List<List<List<Cell>>> cells;
        private Map<Integer, String> charMap = new HashMap<>();
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
        this.size = order * order;
        this.cells = new ArrayList<>(size);
    }


    public void makeBoard(Boolean addDummyData) {
        CellFactory factory = new CellFactory(order);
        for (int i = 0; i < size; i++) {
            ArrayList<List<Cell>> cols = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                ArrayList<Cell> nums = new ArrayList<>(size);
                for (int k = 0; k < size; k++) {
                    Cell cell;
                    if (addDummyData) {
                        cell = factory.getFilledCell(i, j, k);
                    } else {
                        cell = factory.getFilledCell(i, j, k);
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
        // i is the overall row
        for (int i = 0; i < size; i++) {
            StringBuilder line = new StringBuilder("");
            // j is the 'number'
            for (int j = 0; j < size; j++) {
                line.append("| ");
                // k is the column, within each square
                for (int k = 0; k < size; k++) {
                    if (cells.get(i).get(k).get(j).isFilled()) {  // Don't let this freak you out...
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

        FileReader fReader;
        try {
            fReader = new FileReader(fullFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader bReader = new BufferedReader(fReader);

        String letterSet = charMap.get(order);
        try {
            String line = bReader.readLine();
            Integer i = 0;
            while (line != null) {
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) != '.') {
                        Integer index = letterSet.indexOf(line.substring(j, j+1));
                        for (int k = 0; k < size; k++) {
                            if (k != index) {
                                cells.get(i).get(j).get(k).unfill();
                            }
                        }
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
     * Return the character for an individual shaft.  Possibilities are:
     * -- a number, as long as there is one unique cell filled
     * -- a period, as long as there are more than one cell filled
     * -- an X, meaning that no cells are filled, which is an error state
     *
     * @param shaft is a 1-dimensional array
     * @return character
     */
    private Character getChar(List<Cell> shaft) {
        Character value = 'X';
        for (int i = 0; i < shaft.size(); i++) {
            if (shaft.get(i).isFilled()) {
                if (value != 'X') {
                    return '.';
                } else {
                    value = Integer.toString(i + 1).charAt(0);
                }
            }
        }
        return value;
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
            if (shaft.get(i).isFilled()) {
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
    private Integer countFilledCellsToQty(Integer qty) {
        Integer count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (countShaftChoices(cells.get(i).get(j)).equals(qty)) {
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
    public Integer countFilledCells() {
        return countFilledCellsToQty(1);
    }

    /**
     * Returns the number of cells with a true in them.
     * @return
     */
    public Integer sumFilledCells()
    {
        Integer count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (cells.get(i).get(j).get(k).isFilled())
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public Integer getSize()
    {
        return size;
    }

    public Integer getOrder()
    {
        return order;
    }

    public List<List<List<Cell>>> getCells()
    {
        return cells;
    }


    /**
     * This makes 8 versions of the board, according the the solving order.
     * (4D) For each version:
     *    -- potentially make a copy here and spin off threads for the below
     *    (3D -- board) For each (first) index:
     *       (2D -- slice) For each (second) index:
     *           For every 1 row:
     *             if there is one filled cell in one row...
     *           For every pairs of rows:
     *             if there are two filled cells in two rows...
     *           For every three rows:
     *             if there are three filled cells in three rows...
     *           ...remove from all other rows
     *   -- aggregate the results for each of the 8 boards here ---
     *
     * @return 8 versions of the board
     */
    public List<List<List<List<Cell>>>> transpose()
    {
        List<List<List<List<Cell>>>> permutations = new ArrayList<>(8);
        for (int h = 0; h < 8; h++) {
            List<List<List<Cell>>> outer = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                List<List<Cell>> mid = new ArrayList<>(size);
                for (int j = 0; j < size; j++) {
                    mid.add(new ArrayList<>(size));
                }
                outer.add(mid);
            }
            permutations.add(outer);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    Cell thisOne = cells.get(i).get(j).get(k);
                    permutations.get(0).get(i).get(j).add(thisOne);
                    permutations.get(1).get(j).get(i).add(thisOne);

                    thisOne = cells.get(i).get(k).get(j);
                    permutations.get(2).get(i).get(j).add(thisOne);
                    permutations.get(3).get(j).get(i).add(thisOne);

                    thisOne = cells.get(k).get(i).get(j);
                    permutations.get(4).get(i).get(j).add(thisOne);
                    permutations.get(5).get(j).get(i).add(thisOne);

                    int box =  i / order + (j / order) * order;
                    thisOne = cells.get(i).get(j).get(k);
                    permutations.get(6).get(box).get(k).add(thisOne);
                    permutations.get(7).get(k).get(box).add(thisOne);
                }
            }
        }
        return permutations;
    }
}
