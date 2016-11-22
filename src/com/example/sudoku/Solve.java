package com.example.sudoku;


import java.util.HashMap;

/**
 * Created by Eric on 11/16/2016.
 *
 * Main function for the Sudoku puzzle solver.
 */
public class Solve {

    public static void main(String[] args) {
        System.out.println("Hello World.");
        Integer order = Integer.parseInt(args[0]);
        Board board = new Board();
        board.init();
        board.makeBoard(order, false);
        board.loadBoard();
        board.display3dBoard();
        board.displayBoard();
        board.solveBoard();
    }
}
