package com.example.sudoku;

import java.util.ArrayList;
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
 *      7  B N I (index)
 *      8  B I N
 */
class Slice {

    private List<Shaft> shafts;

    Slice(int size) {
        this.shafts = new ArrayList<>(size);
    }

    Slice(List<Shaft> shafts) {
        this.shafts = new ArrayList<>(shafts);
    }

    Slice(Slice rhs) {
        this.shafts = new ArrayList<>(rhs.getShafts());  // <-- It turns out that this is very important in making a copy
    }

    List<Shaft> getShafts() {
        return shafts;
    }

    Shaft getShaft(int i) {
        return shafts.get(i);
    }

    void addShaft(Shaft shaft)
    {
        if (shafts == null)
        {
            shafts = new ArrayList<>();
        }
        this.shafts.add(shaft);
    }

    void addShaft(int i, Shaft shaft)
    {
        if (shafts == null)
        {
            shafts = new ArrayList<>();
        }
        this.shafts.add(i, shaft);
    }

    void removeAllShafts(List<Shaft> shafts)
    {
        this.shafts.removeAll(shafts);
    }

    int getSize()
    {
        return shafts.size();
    }

    Slice subList(int start, int stop)
    {
        return new Slice(getShafts().subList(start, stop));
    }
}
