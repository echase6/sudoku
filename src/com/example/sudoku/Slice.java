package com.example.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eric on 12/12/2016.
 *
 * This class holds a 2-D slice of the 3-D board
 *
 * There should be 8 total:
 *  3 per face, x 2 for row-first or column-first (6)
 *  1 more for each box, x 2 for box-iterator-first or depth-iterator-first (2)
 *
 *  Mapping:
 *      1  R C N
 *      2  R N C
 *      3  C R N
 *      4  C N R
 *      5  N R C
 *      6  N C R
 *      7  B N
 *      8  N B
 */
public class Slice {

    private List<List<Cell>> cells;
    private Integer sliceNum;

    public Slice(List<List<Cell>> cells, int sliceNum) {
        this.cells = cells;
        this.sliceNum = sliceNum;
    }

    public List<List<Cell>> getCells()
    {
        return cells;
    }

    public Integer getSliceNum() {
        return sliceNum;
    }

    public List<List<Integer>> getIndices()
    {
        List<List<Integer>> indices = null;
        indices.add(Arrays.asList(0, 0, 0));
        indices.add(Arrays.asList(0, 1, 0));
        indices.add(Arrays.asList(0, 2, 0));
        indices.add(Arrays.asList(0, 3, 0));
        indices.add(Arrays.asList(1, 0, 0));
        indices.add(Arrays.asList(1, 1, 0));
        indices.add(Arrays.asList(1, 2, 0));
        indices.add(Arrays.asList(1, 3, 0));
        indices.add(Arrays.asList(2, 0, 0));
        indices.add(Arrays.asList(2, 1, 0));
        indices.add(Arrays.asList(2, 2, 0));
        indices.add(Arrays.asList(2, 3, 0));
        indices.add(Arrays.asList(3, 0, 0));
        indices.add(Arrays.asList(3, 1, 0));
        indices.add(Arrays.asList(3, 2, 0));
        indices.add(Arrays.asList(3, 3, 0));

        return indices;
    }


}
