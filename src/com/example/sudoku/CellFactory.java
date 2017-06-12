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
class CellFactory {
    private int order;

    CellFactory(int order)
    {
        this.order = order;
    }

    Cell getFilledCell(int row, int col, int num)
    {
        int box =  col / order + (row / order) * order;
        return new Cell(row, col, box, num, true);
    }

    Cell getEmptyCell(int row, int col, int num)
    {
        int box =  col / order + (row / order) * order;
        return new Cell(row, col, box, num, false);
    }
}
