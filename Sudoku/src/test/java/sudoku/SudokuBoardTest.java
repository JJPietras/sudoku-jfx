package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuBoardTest {

    private final SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void sudokuBoardExceptionsTest() {
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
    public void getSetFieldMethodTest() {
        Assertions.assertEquals(sudokuBoard.getField(0, 0), 0);
        sudokuBoard.setField(0, 0, 1);
        Assertions.assertEquals(sudokuBoard.getField(0, 0), 1);
    }

    @Test
    public void toStringTest() {
        //System.out.println(sudokuBoard.toString());
    }

    @Test
    public void equalsTest() {
        Assertions.assertFalse(sudokuBoard.equals(null));
        Assertions.assertTrue(sudokuBoard.equals(sudokuBoard));

        sudokuBoard.setField(0, 0, 2);
        SudokuBoard sudokuBoard1 = sudokuBoard;
        Assertions.assertTrue(sudokuBoard.equals(sudokuBoard1));

        sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        Assertions.assertFalse(sudokuBoard.equals(sudokuBoard1));

        for (int i = 0; i < Consts.SIZE; i++) {
            for (int j = 0; j < Consts.SIZE; j++) {
                sudokuBoard.setField(i, j, 1);
                sudokuBoard1.setField(i, j, 1);
            }
        }
        Assertions.assertTrue(sudokuBoard.equals(sudokuBoard1));
    }
}