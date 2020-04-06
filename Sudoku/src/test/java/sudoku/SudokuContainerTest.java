package sudoku;

import org.apache.commons.lang.NullArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuContainerTest {

    @Test
    void sudokuContainerExceptionsTest() {
        Assertions.assertThrows(
                NullArgumentException.class,
                () -> new SudokuColumn(null));

        Assertions.assertThrows(
                NullArgumentException.class,
                () -> new SudokuColumn(new SudokuField[]
                        {new SudokuField(), null, null, null, null, null, null, null, null}));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new SudokuColumn(new SudokuField[4]));
    }

    @Test
    void sudokuContainerTest() {
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
}