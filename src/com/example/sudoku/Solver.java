package com.example.sudoku;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Eric on 12/12/2016.
 * <p>
 * This class is where the solver algorithms will reside.
 */
class Solver {
    private Board board = null;
    private int iter = 8;

    Solver(Board board, int iter) {
        this.board = board;
        this.iter = iter;
    }


    void run() {
        int filled = board.getCube().sumFilledCells();

        int counter = 0;
        int newFilled = filled;
        Boolean done;

        List<Cube> slicedBoard = board.transpose();

        do {
            filled = newFilled;

            for (Cube boardIter: slicedBoard)
            {
                for (Slice slice: boardIter.getSlices())
                {
                    processSlice(slice);
                    newFilled = board.getCube().sumFilledCells();
                    counter++;
                    board.displayBoard();
                    System.out.println("Filled = " + newFilled + " Counter = " + counter + "\033[K");

                    if (isDone(board))
                    {
                        break;
                    }
                }
                if (isDone(board))
                {
                    break;
                }
            }
            done = isDone(board);

        } while (!done && filled != newFilled);
    }

    /**
     *           For every 1 row:
     *             if there is one filled cell in one row...
     *           For every pairs of rows:
     *             if there are two filled cells in two rows...
     *           For every three rows:
     *             if there are three filled cells in three rows...
     *           ...remove from all other rows
     */
    private void processSlice (Slice trialSlice)
    {
        for (int i = 1; i <= iter; i++) {
            List<Pair<Slice, Slice>> trialPerms  = Permute.getPermPairs(trialSlice, i);
            for (Pair<Slice, Slice> trial: trialPerms) {
                List<Boolean> collapsed = collapseSlice(trial.getKey());
                if (countFilled(collapsed) == i)
                {
                    updateSlice(trial.getValue(), collapsed);
                }
            }
        }
    }


    private List<Boolean> collapseSlice(Slice slice)
    {
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < slice.getShaft(0).getSize(); i++)
        {
            result.add(false);
            for (Shaft shaft: slice.getShafts())
            {
                if (shaft.getCell(i).isFilled())
                {
                    result.set(i, true);
                }
            }
        }

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


    private void updateSlice(Slice slice, List<Boolean> collapsed)
    {
        for (int i = 0; i < collapsed.size(); i++)
        {
            for (Shaft shaft: slice.getShafts())
            {
                if (collapsed.get(i))
                {
                    shaft.getCell(i).unfill();  // <-- Here is where progress is made.
                }
            }
        }
    }


    private boolean isDone(Board board) {
        int targetQuantity = board.getSize() * board.getSize();
        int filledQuantity = board.getCube().countFilledCells();
        return (targetQuantity == filledQuantity);
    }
}
