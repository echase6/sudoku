package com.example.sudoku;

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

    public Solver(Board board) {
        this.board = board;
    }

    public void init() {
        List<Slice> slices = new ArrayList<>();
    }

    public void run() {
        Board results;
        Boolean done = false;
        Boolean stalled = false;
        while (!done && !stalled) {
            List<Slice> sliceResults = null;
            for (Slice slice : slices)    // This is where multi-treading may be used.
            {
                sliceResults.add(getSliceResults(slice));
            }
            results = aggregateResults(sliceResults);
            depopulateBoard(board, results);
            done = isDone(board);
            stalled = isStalled(results);
        }
    }

    private Slice getSliceResults(Slice slice) {
        return slice;
    }

    private Board aggregateResults(List<Slice> sliceResults) {
        int order = Board.getOrder();
        Board results = new Board(order);
        Boolean[][][] resultCells = results.getCells();
        for (Slice sliceResult: sliceResults)
        {
            Boolean[][] sliceCells = sliceResult.getCells();
            for (List<Integer> index: sliceResult.getIndices())
            {
                int i = index.get(0);
                int j = index.get(1);
                int k = index.get(2);
                if (!sliceCells[i][j]) {
                    resultCells[i][j][k] = false;
                }
            }
        }
        return results;
    }

    private void depopulateBoard(Board board, Board results) {
        int size = board.getSize();
        Boolean[][][] boardCells = board.getCells();
        Boolean[][][] resultsCells = results.getCells();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (!resultsCells[i][j][k])  // TODO:  Make sure the sense of this is correct.
                    {
                        boardCells[i][j][k] = false;
                    }
                }
            }
        }
    }

    private boolean isDone(Board board) {
        int targetQuantity = board.getSize();
        int filledQuantity = board.countFilledCells(board);
        return (targetQuantity == filledQuantity);
    }

    private boolean isStalled(Board results) {
        int resultsQuantity = results.countFilledCells(results);
        return (resultsQuantity == 0);
    }

    public Board unload() {
        return board;
    }
}
