package com.example.sudoku;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Eric on 12/12/2016.
 * <p>
 * This class is where the solver algorithms will reside.
 */
public class Solver {
    private Board board = null;
    private List<Slice> slices = null;
    private int iter = 3;

    public Solver(Board board) {
        this.board = board;
    }

    public void init() {
        slices = new ArrayList<>(board.getSize());
    }


    public void run() {
        int filled = Board.sumFilledCells(board.getCells(), board.getSize());
        System.out.println("Originally filled = " + filled);

        int newFilled = filled;
        Boolean done;

        List<List<List<List<Cell>>>> slicedBoard = board.transpose();

        do {
            filled = newFilled;

            for (List<List<List<Cell>>> boardIter: slicedBoard)
            {
                processBoardIter(boardIter);
            }

            newFilled = Board.sumFilledCells(board.getCells(), board.getSize());
            board.display3dBoard();
            board.displayBoard();
            System.out.println("Filled = " + newFilled);
            done = isDone(board);

        } while (!done && filled != newFilled);
    }


    private void processBoardIter(List<List<List<Cell>>> boardIter)
    {
        for (List<List<Cell>> slice: boardIter)
        {
            processSlice(slice);
        }
    }


    private void processSlice (List<List<Cell>> trialSlice)
    {
        for (int i = 1; i <= iter; i++) {
            List<Pair<List<List<Cell>>, List<List<Cell>>>> trialPerms  = Recursion.getPermPairs(trialSlice, i);
            for (Pair<List<List<Cell>>, List<List<Cell>>> trial: trialPerms) {
                List<Boolean> collapsed = collapseSlice(trial.getKey());
//                System.out.println("Collapsed: '" + collapsed + "'");
                if (countFilled(collapsed) == i)
                {
                    updateSlice(trial.getValue(), collapsed);
                }
            }
        }
    }


    private List<Boolean> collapseSlice(List<List<Cell>> slice)
    {
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < slice.get(0).size(); i++)
        {
            result.add(false);
            for (List<Cell> shaft: slice)
            {
                if (shaft.get(i).isFilled())
                {
                    result.set(i, true);
                }
            }
        }

//        for (List<Cell> shaft: slice)
//        {
//            System.out.println("Shaft: " + shaft);
//        }
//        System.out.println("  Collapsed: " + result);


        return result;
    }


    private int countFilled(List<Boolean> collapsed)
    {
        int count = 0;
        for (Boolean cell: collapsed)
        {
            if (cell)
            {
                count++;
            }
        }
        return count;
    }


    private void updateSlice(List<List<Cell>> slice, List<Boolean> collapsed)
    {
        for (int i = 0; i < collapsed.size(); i++)
        {
            for (List<Cell> shaft: slice)
            {
                if (collapsed.get(i))
                {
                    shaft.get(i).unfill();
                }
            }
        }
    }


//    private Slice getSliceResults(Slice slice) {
//
//        CellFactory factory = new CellFactory(board.getOrder());
//
//        Cell cell0 = factory.getEmptyCell(0, 0, 0);
//        Cell cell1 = factory.getEmptyCell(1, 0, 0);
//        Cell cell2 = factory.getEmptyCell(2, 0, 0);
//        Cell cell3 = factory.getEmptyCell(3, 0, 0);
//
//        List<Cell> row0 = Arrays.asList(cell0, cell1);
//        List<Cell> row1 = Arrays.asList(cell2, cell3);
//        List<List<Cell>> sliceList = Arrays.asList(row0, row1);
//
//        return new Slice(sliceList, 1);
//    }

//    private Board aggregateResults(List<List<List<List<Cell>>>> sliceResults) {
//        int order = board.getOrder();
//        Board results = new Board(order);
//        results.makeBoard(false);
//        List<List<List<Cell>>> resultCells = results.getCells();
//        for (List<List<List<Cell>>> sliceResult: sliceResults)
//        {
//            for (List<Integer> index: sliceResult.getIndices())
//            {
//                int i = index.get(0);
//                int j = index.get(1);
//                int k = index.get(2);
//                if (!sliceCells.get(i).get(j).isFilled()) {
//                    resultCells.get(i).get(j).get(k).fill();
//                }
//            }
//        }
//        return results;
//    }

//    private void depopulateBoard(Board board, List<List<List<Cell>>> resultsCells) {
//        // Un-fill all cells that are filled in the results
//
//        int size = board.getSize();
//        List<List<List<Cell>>> boardCells = board.getCells();
////        List<List<List<Cell>>> resultsCells = results.getCells();
//        if (resultsCells != null && !resultsCells.isEmpty()) {
//            for (int i = 0; i < size; i++) {
//                for (int j = 0; j < size; j++) {
//                    for (int k = 0; k < size; k++) {
//                        if (!resultsCells.get(i).get(j).get(k).isFilled())  // TODO:  Make sure the sense of this is correct.
//                        {
//                            boardCells.get(i).get(j).get(k).unfill();
//                        }
//                    }
//                }
//            }
//        }
//    }


    private boolean isDone(Board board) {
        int targetQuantity = board.getSize() * board.getSize();
        int filledQuantity = Board.countFilledCells(board.getCells(), board.getSize());

        System.out.println("Target: " + targetQuantity + ", Filled: " + filledQuantity);
        return (targetQuantity == filledQuantity);
    }


//    private boolean isStalled(List<List<List<Cell>>> results) {
//        if (results == null)
//        {
//            return true;
//        }
//        int resultsQuantity = Board.countFilledCells(results, board.getSize());
//        return (resultsQuantity == 0);
//    }

    public Board unload() {
        return board;
    }
}
