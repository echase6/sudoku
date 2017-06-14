package com.example.sudoku;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 6/4/2017.
 *
 * This is an exercise in recursion, to generate permutations and combinations of an inputted list.
 *
 * The makeCombo is here as an (unused) freebee.
 *
 * The makePerm is used for the Sudoku solver in grabbing combinations of rows for processing
 * Since the rows to be modified are exclusive of the rows to be inspected, a getPermPairs() wrapper
 *   is added to return the combinations and the rows excluded from the combinations.
 *
 * This is the other heavy-lifting algorithm in the Sudoku solver.
 */
public class Recursion {

    public static void main(String[] args) {

        List<List<String>> mid = new ArrayList<>();
        List<String> start0 = new ArrayList<>();
        start0.add("a");
        start0.add("b");
        start0.add("c");
        start0.add("d");
        mid.add(start0);

        List<String> start1 = new ArrayList<>();
        start1.add("e");
        start1.add("f");
        start1.add("g");
        start1.add("h");
        mid.add(start1);

        List<String> start2 = new ArrayList<>();
        start2.add("i");
        start2.add("j");
        start2.add("k");
        start2.add("l");
        mid.add(start2);

        List<String> start3 = new ArrayList<>();
        start3.add("m");
        start3.add("n");
        start3.add("o");
        start3.add("p");
        mid.add(start3);

        List<Pair<List<List<String>>, List<List<String>>>> toReturn = new ArrayList<>();
        List<List<List<String>>> result = makePerm(mid, 2);
        System.out.println(result);

        for (List<List<String>> oneResult: result)
        {
            List<List<String>> remainder = new ArrayList<>(mid);
            remainder.removeAll(oneResult);
            toReturn.add(new Pair(oneResult, remainder));

            System.out.println("one result: " + oneResult);
            System.out.println("remainder: " + remainder);
            System.out.println("");
        }

        System.out.println(toReturn);
    }

    static <T extends Object> List<Pair<List<List<T>>, List<List<T>>>> getPermPairs(List<List<T>> inList, int n)
    {
        List<Pair<List<List<T>>, List<List<T>>>> toReturn = new ArrayList<>();
        List<List<List<T>>> result = makePerm(inList, n);

        for (List<List<T>> oneResult: result)
        {
            List<List<T>> remainder = new ArrayList<>(inList);
            remainder.removeAll(oneResult);
            toReturn.add(new Pair(oneResult, remainder));
        }

        return toReturn;
    }

    private static <T extends Object> List<List<List<T>>> makePerm(List<List<T>> inList, int n) {
//        System.out.println("Just got called with n: " + n + " and inList: " + inList);

        // If n==1, that means return each element of the input list as a list of single elements
        // This is the terminating condition for recursion
        List<List<List<T>>> newList = new ArrayList<>();
        if (n == 1) {
            for (List<T> item : inList) {
                List<List<T>> firstList = new ArrayList<>();
                firstList.add(item);
                newList.add(firstList);
            }
            return newList;
        }

        // Otherwise, slice the list upward until n elements remain, and call this again on n - 1
        // Decrementing n is how progress is made to terminating the recursion
        for (int i = 0; i < inList.size() - n + 1; i++) {
            List<List<List<T>>> suffixList = makePerm(inList.subList(i + 1, inList.size()), n - 1);

            // With each list that came back, prepend the i-th element
            for (List<List<T>> suffix : suffixList) {
                suffix.add(0, inList.get(i));
                newList.add(suffix);
            }
        }
        return newList;
    }


    private static <T extends Object> List<List<List<T>>> makeCombo(List<List<T>> inList, int n) {
//        System.out.println("Just got called with n: " + n + " and inList: " + inList);

        // If n==1, that means return each element of the input list as a list of single elements
        // This is the terminating condition for recursion
        List<List<List<T>>> newList = new ArrayList<>();
        if (n == 1) {
            for (List<T> item : inList) {
                List<List<T>> firstList = new ArrayList<>();
                firstList.add(item);
                newList.add(firstList);
            }
            return newList;
        }

        // Otherwise, slice the list upward until n elements remain, and call this again on n - 1
        // Decrementing n is how progress is made to terminating the recursion
        for (int i = 0; i < inList.size() - n + 1; i++) {
            List<List<List<T>>> suffixList = makeCombo(inList.subList(i + 1, inList.size()), n - 1);

            // With each list that came back, prepend the i-th element
            for (List<List<T>> suffix : suffixList)
            {
                // Inserting the single into all locations of the suffix turns them into combinations
                for (int j = 0; j < suffix.size() + 1; j++)
                {
                    List<List<T>> newSuffix = new ArrayList<>(suffix);
                    newSuffix.add(j, inList.get(i));
                    newList.add(newSuffix);
                }
            }
        }
        return newList;
    }
}
