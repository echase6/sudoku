package com.example.sudoku;

import javafx.util.Pair;
import sun.security.provider.SHA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 6/4/2017.
 *
 * This is an exercise in recursion, to generate permutations and combinations of an inputted list.
 **
 * The makePerm is used for the Sudoku solver in grabbing combinations of rows for processing
 * Since the rows to be modified are exclusive of the rows to be inspected, a getPermPairs() wrapper
 *   is added to return the combinations and the rows excluded from the combinations.
 *
 * This is the other heavy-lifting algorithm in the Sudoku solver.
 */
public class Permute {



    static <T extends Object> List<Pair<Slice, Slice>> getPermPairs(Slice slice, int n)
    {
        List<Pair<Slice, Slice>> toReturn = new ArrayList<>();
        List<Slice> result = makePerm(slice, n);

        for (Slice oneResult: result)
        {
            Slice remainder = new Slice(slice);
            remainder.removeAllShafts(oneResult.getShafts());
            toReturn.add(new Pair(oneResult, remainder));
        }

        return toReturn;
    }

    private static <T extends Object> List<Slice> makePerm(Slice inList, int n) {
//        System.out.println("Just got called with n: " + n + " and inList: " + inList);

        // If n==1, that means return each element of the input list as a list of single elements
        // This is the terminating condition for recursion
        List<Slice> newSliceList = new ArrayList<>();
        if (n == 1) {
            for (Shaft shaft : inList.getShafts()) {
                Slice newSlice = new Slice(inList.getSize());
                newSlice.addShaft(shaft);
                newSliceList.add(newSlice);
            }
            return newSliceList;
        }

        // Otherwise, slice the list upward until n elements remain, and call this again on n - 1
        // Decrementing n is how progress is made to terminating the recursion
        for (int i = 0; i < inList.getSize() - n + 1; i++) {
            List<Slice> suffixList = makePerm(inList.subList(i + 1, inList.getSize()), n - 1);

            // With each list that came back, prepend the i-th element
            for (Slice suffix : suffixList) {
                suffix.addShaft(0, inList.getShaft(i));
                newSliceList.add(suffix);
            }
        }
        return newSliceList;
    }
}
