package com.example.sudoku;

import java.util.List;

/**
 * Created by Eric on 12/14/2016.
 *
 *     RCB
 *    +---+---+---+---+
 *    |000|010|021|031|
 *    +---+---+---+---+
 *    |100|110|121|131|
 *    +---+---+---+---+
 *    |202|212|223|233|
 *    +---+---+---+---+
 *    |302|312|323|333|
 *    +---+---+---+---+
 *
 *
 *
 */
public class Cell extends Object{
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

    public void set(Cell rhs)
    {
        this.row = rhs.row;
        this.col = rhs.col;
        this.box = rhs.box;
        this.num = rhs.num;
        this.filled = rhs.filled;
    }

    public int getBox()
    {
        return box;
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

    public Cell(List<String> indices, List<Integer> values)
    {
        for (int i = 0; i < indices.size(); i++)
        {

        }
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
