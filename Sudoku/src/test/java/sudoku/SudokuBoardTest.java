package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuBoardTest {

    private final SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    void sudokuBoardExceptionsTest() {
        Assertions.assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> sudokuBoard.getField(-1, 4));

        Assertions.assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> sudokuBoard.setField(-1, 4, 1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.setField(1, 4, -1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getColumn(-1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getColumn(9));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getRow(-1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getRow(9));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getBox(-1, 5));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getBox(5, -1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getBox(9, 4));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getBox(5, 9));
    }

    @Test
    public void getSetFieldMethodTestCase() {
        Assertions.assertEquals(sudokuBoard.getField(0, 0), 0);
        sudokuBoard.setField(0, 0, 1);
        Assertions.assertEquals(sudokuBoard.getField(0, 0), 1);
    }
}