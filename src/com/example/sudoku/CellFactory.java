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
public class CellFactory {
    private int order;

    public CellFactory(int order)
    {
        this.order = order;
    }

    public Cell getFilledCell(int row, int col, int num)
    {
        int box =  col / order + (row / order) * order;
        return new Cell(row, col, box, num, true);
    }

    public Cell getEmptyCell(int row, int col, int num)
    {
        int box =  col / order + (row / order) * order;
        return new Cell(row, col, box, num, false);
    }
}
