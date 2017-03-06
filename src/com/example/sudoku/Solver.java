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
        slices = new ArrayList<>(board.getSize());}

    public void run() {
        Board results;
        Boolean done = false;
        Boolean stalled = false;
        while (!done && !stalled) {
            List<Slice> sliceResults = new ArrayList<>(board.getSize());
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
        int order = board.getOrder();
        Board results = new Board(order);
        results.makeBoard(false);
        List<List<List<Cell>>> resultCells = results.getCells();
        for (Slice sliceResult: sliceResults)
        {
            List<List<Cell>> sliceCells = sliceResult.getCells();
            for (List<Integer> index: sliceResult.getIndices())
            {
                int i = index.get(0);
                int j = index.get(1);
                int k = index.get(2);
                if (!sliceCells.get(i).get(j).isFilled()) {
                    resultCells.get(i).get(j).get(k).fill();
                }
            }
        }
        return results;
    }

    private void depopulateBoard(Board board, Board results) {
        int size = board.getSize();
        List<List<List<Cell>>> boardCells = board.getCells();
        List<List<List<Cell>>> resultsCells = results.getCells();
        if (resultsCells != null && !resultsCells.isEmpty()) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        if (!resultsCells.get(i).get(j).get(k).isFilled())  // TODO:  Make sure the sense of this is correct.
                        {
                            boardCells.get(i).get(j).get(k).unfill();
                        }
                    }
                }
            }
        }
    }

    private boolean isDone(Board board) {
        int targetQuantity = board.getSize();
        int filledQuantity = board.countFilledCells();
        return (targetQuantity == filledQuantity);
    }

    private boolean isStalled(Board results) {
        if (results == null)
        {
            return true;
        }
        int resultsQuantity = results.countFilledCells();
        return (resultsQuantity == 0);
    }

    public Board unload() {
        return board;
    }
}
