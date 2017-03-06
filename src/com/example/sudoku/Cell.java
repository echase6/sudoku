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

    public void fill()
    {
        this.filled = true;
    }

    public void unfill()
    {
        this.filled = false;
    }

    public void setLocation(int row, int col, int box, int num)
    {
        this.row = row;
        this.col = col;
        this.box = box;
        this.num = num;
    }

    public boolean isFilled()
    {
        return filled;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Cell{");
        sb.append(" row: ").append(row);
        sb.append(" col: ").append(col);
        sb.append(" box: ").append(box);
        sb.append(" num: ").append(num);
        sb.append(" filled ").append(filled);
        sb.append("}");

        return sb.toString();
    }
}
