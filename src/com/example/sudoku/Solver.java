package com.example.sudoku;

import java.util.ArrayList;
import java.util.List;

import static com.example.sudoku.Board.size;

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
        List<Slice> slices = new ArrayList<>(size);
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
        List<List<List<Cell>>> resultCells = results.getCells();
        for (Slice sliceResult: sliceResults)
        {
            List<List<Cell>> sliceCells = sliceResult.getCells();
            for (List<Integer> index: sliceResult.getIndices())
            {
                int i = index.get(0);
                int j = index.get(1);
                int k = index.get(2);
                if (!sliceCells.get(i).get(j).getFilled()) {
                    resultCells.get(i).get(j).get(k).setFilled(false);
                }
            }
        }
        return results;
    }

    private void depopulateBoard(Board board, Board results) {
        int size = board.getSize();
        List<List<List<Cell>>> boardCells = board.getCells();
        List<List<List<Cell>>> resultsCells = results.getCells();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (!resultsCells.get(i).get(j).get(k).getFilled())  // TODO:  Make sure the sense of this is correct.
                    {
                        boardCells.get(i).get(j).get(k).setFilled(false);
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
