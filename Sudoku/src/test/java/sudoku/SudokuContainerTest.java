package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SudokuContainerTest {
    private final SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void constructorTest() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> new SudokuColumn(null));

        Assertions.assertThrows(
                NullPointerException.class,
                () -> new SudokuColumn(Arrays.asList(new SudokuField(), null, null, null, null, null, null, null, null)));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new SudokuColumn(Arrays.asList(new SudokuField[4])));
    }

    @Test
    public void verifyTest() {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        SudokuRow row = testBoard.getRow(0);
        SudokuColumn column = testBoard.getColumn(0);
        SudokuBox box = testBoard.getBox(0, 0);

        Assertions.assertFalse(row.verify());
        Assertions.assertFalse(column.verify());
        Assertions.assertFalse(box.verify());

        testBoard.solveGame();
        row = testBoard.getRow(0);
        column = testBoard.getColumn(0);
        box = testBoard.getBox(0, 0);

        Assertions.assertTrue(row.verify());
        Assertions.assertTrue(column.verify());
        Assertions.assertTrue(box.verify());
    }

    @Test
    public void toStringTest() {
        SudokuRow firstRow = testBoard.getRow(0);
        SudokuRow secondRow = testBoard.getRow(1);
        Assertions.assertEquals(firstRow.toString(), firstRow.toString());
        Assertions.assertNotEquals(firstRow.toString(), secondRow.toString());
    }

    @Test
    public void equalsTest() {
        SudokuRow firstRow = testBoard.getRow(0);
        SudokuRow secondRow = testBoard.getRow(1);
        SudokuColumn column = testBoard.getColumn(0);

        Assertions.assertEquals(firstRow, firstRow);
        Assertions.assertNotEquals(firstRow, null);
        Assertions.assertNotEquals(firstRow, column);

        Assertions.assertEquals(firstRow, secondRow);

        testBoard.setField(0, 0, 2);
        Assertions.assertNotEquals(firstRow, secondRow);
        testBoard.setField(0, 1, 2);
        Assertions.assertEquals(firstRow, secondRow);
    }

    @Test
    public void hashCodeTest() {
        testBoard.setField(0, 1, 5);

        Assertions.assertEquals(
                testBoard.getRow(0).hashCode(),
                testBoard.getRow(0).hashCode());

        Assertions.assertNotEquals(
                testBoard.getRow(0).hashCode(),
                testBoard.getRow(1).hashCode());
    }
}