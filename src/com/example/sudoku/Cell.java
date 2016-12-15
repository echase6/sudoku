package com.example.sudoku;

/**
 * Created by Eric on 12/14/2016.
 *
 *
 */
public class Cell {
    private int row;
    private int col;
    private int box;
    private int num;
    private boolean filled;

    public Cell(int row, int col, int box, int num, boolean filled)
    {
        this.row = row;
        this.col = col;
        this.box = box;
        this.num = num;
        this.filled = filled;
    }

    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }

    public boolean getFilled()
    {
        return filled;
    }
}
