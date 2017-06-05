package com.example.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 6/4/2017.
 *
 * This is a somewhat academic exercise in recrusion, to generate permutations of an inputted list.
 */
public class Recursion {
    public static void main(String[] args)
    {
        List<String> start = new ArrayList<>();
        start.add("a");
        start.add("b");
        start.add("c");
        start.add("d");
        start.add("e");
        start.add("f");

        List<List<String>> result = makePerm(start, 3);

        for (List<String> element: result)
        {
            System.out.println(element);
        }
    }

    private static List<List<String>> makePerm(List<String> inList, int n)
    {
        System.out.println("Just got called with n: " + n + " and inList: " + inList);

        // If n==1, that means return each element of the input list as a list of single elements
        // This is the terminating condition for recursion
        List<List<String>> newList = new ArrayList<>();
        if (n == 1)
        {
            for (String item: inList)
            {
                List<String> firstList = new ArrayList<>();
                firstList.add(item);
                newList.add(firstList);
            }
            return newList;
        }

        // Otherwise, slice the list upward until n elements remain, and call this again on n - 1
        // Decrementing n is how progress is made to terminating the recursion
        for (int i = 0; i < inList.size() - n + 1; i++)
        {
            List<List<String>> suffixList = makePerm(inList.subList(i + 1, inList.size()), n - 1);

            // With each list that came back, prepend the i-th element
            for (List<String> suffix: suffixList)
            {
                suffix.add(0, inList.get(i));
                newList.add(suffix);
            }
        }
        return newList;
    }
}
