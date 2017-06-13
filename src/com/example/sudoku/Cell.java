package com.example.sudoku;

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
class Cell {
    private int row;
    private int col;
    private int box;
    private int num;
    private boolean filled;

    Cell(int row, int col, int box, int num, boolean filled)
    {
        this.row = row;
        this.col = col;
        this.box = box;
        this.num = num;
        this.filled = filled;
    }


    void unfill()
    {
        this.filled = false;
    }


    boolean isFilled()
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
