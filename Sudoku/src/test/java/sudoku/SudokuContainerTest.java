package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SudokuContainerTest {

    @Test
    public void sudokuContainerExceptionsTest() {
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
    public void sudokuContainerTest() {
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

    }

    @Test
    public void equalsTest() {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        SudokuRow row = testBoard.getRow(0);
        SudokuRow row1 = testBoard.getRow(1);
        SudokuColumn column = testBoard.getColumn(0);

        Assertions.assertTrue(row.equals(row));
        Assertions.assertFalse(row.equals(null));
        Assertions.assertFalse(row.equals(column));

        Assertions.assertTrue(row.equals(row1));

        testBoard.setField(0, 0, 2);
        Assertions.assertFalse(row.equals(row1));
        testBoard.setField(0, 1, 2);
        Assertions.assertTrue(row.equals(row1));
    }
}