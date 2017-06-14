package com.example.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 6/12/2017.
 *
 *
 */
class Shaft {
    private List<Cell> cells;

    Shaft(int size)
    {
        this.cells = new ArrayList<>(size);
    }

    void addCell(Cell cell)
    {
        if (cells == null)
        {
            cells = new ArrayList<>();
        }
        this.cells.add(cell);
    }

    Cell getCell(int i)
    {
        return cells.get(i);
    }

    int getSize()
    {
        return cells.size();
    }

    int countChoices() {
        int count = 0;
        for (Cell aShaft : cells) {
            if (aShaft.isFilled()) {
                count++;
            }
        }
        return count;
    }
}
