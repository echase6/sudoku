package com.example.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 6/12/2017.
 *
 *
 */
class Cube {
    private List<Slice> slices;

    Cube(int size)
    {
        this.slices = new ArrayList<>(size);
    }

    void addSlice(Slice slice)
    {
        if (slices == null)
        {
            slices = new ArrayList<>();
        }
        this.slices.add(slice);
    }

    List<Slice> getSlices()
    {
        return slices;
    }

    Slice getSlice(int i)
    {
        return slices.get(i);
    }

    Integer sumFilledCells()
    {
        int size = slices.size();
        Integer count = 0;
        for (Slice slice : slices) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (slice.getShaft(j).getCell(k).isFilled()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Return the number of cube that are filled to particular quantity.
     *
     * @param qty   the quantity to test to.
     *
     * @return the count of the number of shafts that are filled to the quantity
     */
    private Integer countFilledCellsToQty(int qty) {
        int size = slices.size();
        Integer count = 0;
        for (Slice slice : slices) {
            for (int j = 0; j < size; j++) {
                if (slice.getShaft(j).countChoices() == qty) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Return the number of cube that have only one cell filled.
     *
     * @return the number of cube that are filled
     */
    Integer countFilledCells() {
        return countFilledCellsToQty(1);
    }
}
