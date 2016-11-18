package com.example.sudoku;


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
        board.makeBoard(order);
        board.loadBoard();
        board.displayBoard();
        board.solveBoard();
    }
}
