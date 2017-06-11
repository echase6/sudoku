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
        Board board = new Board(order);
        board.makeBoard(false);
        board.loadBoard();
        board.display3dBoard();
        board.displayBoard();

        Solver solver = new Solver(board);
        solver.init();
        solver.run();
        board = solver.unload();

        board.displayBoard();
    }

}
