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
class Board {
    private Integer order;
    private Integer size;
    private Cube cube;
    private String filename;
    private String directory;

    private static final String ORDER_2_MAP = "1234";
    private static final String ORDER_3_MAP = "123456789";
    private static final String ORDER_4_MAP = "0123456789ABCDEF";
    private Map<Integer, String> charMap = new HashMap<>();


    Board(Integer order, String filename) {
        this.order = order;
        this.filename = filename;

        charMap.put(2, ORDER_2_MAP);
        charMap.put(3, ORDER_3_MAP);
        charMap.put(4, ORDER_4_MAP);
        size = order * order;
        directory = "C:/Users/Eric/IdeaProjects/sudoku/puzzles/order_" + order;
        cube = new Cube(size);

    }


    void makeBoard(Boolean addDummyData) {
        CellFactory factory = new CellFactory(order);
        for (int i = 0; i < size; i++) {
            Slice face = new Slice(size);

            for (int j = 0; j < size; j++) {
                Shaft nums = new Shaft(size);

                for (int k = 0; k < size; k++) {
                    Cell cell;
                    if (addDummyData) {
                        cell = factory.getFilledCell(i, j, k);
                    } else {
                        cell = factory.getFilledCell(i, j, k);
                    }
                    nums.addCell(cell);
                }
                face.addShaft(nums);
            }
            cube.addSlice(face);
        }
    }


    /**
     * displayBoard does the obvious.
     */
    void displayBoard() {

        System.out.println("\033[0;0H"); // Move cursor to top of screen
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
                        line.append(' ').append(getChar(cube.getSlice(i * order + j).getShaft(k * order + l))).append(' ');
                    }
                    line.append('|');
                }
                System.out.println(line);
            }
            System.out.println(border);
        }
    }

    void display3dBoard() {
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
                    if (cube.getSlice(i).getShaft(k).getCell(j).isFilled()) {  // Don't let this freak you out...
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

    void loadBoard() {
        String fullFileName = directory + '/' + filename;
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
                                cube.getSlice(i).getShaft(j).getCell(k).unfill();
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
     * -- an X, meaning that no cube are filled, which is an error state
     *
     * @param shaft is a 1-dimensional array
     * @return character
     */
    private Character getChar(Shaft shaft) {
        Character value = 'X';
        for (int i = 0; i < shaft.getSize(); i++) {
            if (shaft.getCell(i).isFilled()) {
                if (value != 'X') {
                    return '.';
                } else {
                    value = charMap.get(order).charAt(i);
                }
            }
        }
        return value;
    }


    Integer getSize()
    {
        return size;
    }


    Cube getCube()
    {
        return cube;
    }


    /**
     * This makes 8 versions of the board, according the the solving order.
     * This is a heavy-lifting method, very crucial for the overall operation
     *
     * (4D) For each version:
     *    (3D -- board) For each (first) index:
     *       (2D -- slice) For each (second) index:
     *           (1D -- shaft)  For each (third) index:
     *           Get a cell from the board and add it to the shaft
     *             Iterate on the cell and the shaft to make all 3 sides, taken both ways (3*2 = 6 total)
     *           Get a cell from the board and add it to a box-shaft
     *             Iterate on the cell and box-shaft to make B-N-I and B-I-N views
     *
     * @return 8 versions of the board
     */
    List<Cube> transpose()
    {
        List<Cube> permutations = new ArrayList<>(8);

        for (int h = 0; h < 8; h++) {
            Cube outer = new Cube(size);
            for (int i = 0; i < size; i++) {
                Slice mid = new Slice(size);
                for (int j = 0; j < size; j++) {
                    mid.addShaft(new Shaft(size));
                }
                outer.addSlice(mid);
            }
            permutations.add(outer);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    Cell thisOne = cube.getSlice(i).getShaft(j).getCell(k);
                    permutations.get(0).getSlice(i).getShaft(j).addCell(thisOne);
                    permutations.get(1).getSlice(j).getShaft(i).addCell(thisOne);

                    thisOne = cube.getSlice(i).getShaft(k).getCell(j);
                    permutations.get(2).getSlice(i).getShaft(j).addCell(thisOne);
                    permutations.get(3).getSlice(j).getShaft(i).addCell(thisOne);

                    thisOne = cube.getSlice(k).getShaft(i).getCell(j);
                    permutations.get(4).getSlice(i).getShaft(j).addCell(thisOne);
                    permutations.get(5).getSlice(j).getShaft(i).addCell(thisOne);

                    int box =  j / order + (i / order) * order;
                    int iter = j % order + (i % order) * order;
                    thisOne = cube.getSlice(i).getShaft(j).getCell(k);
                    permutations.get(6).getSlice(box).getShaft(iter).addCell(thisOne);

                    box =  i / order + (k / order) * order;
                    iter = i % order + (k % order) * order;
                    thisOne = cube.getSlice(k).getShaft(i).getCell(j);
                    permutations.get(7).getSlice(box).getShaft(iter).addCell(thisOne);
                }
            }
        }

        return permutations;
    }
}
