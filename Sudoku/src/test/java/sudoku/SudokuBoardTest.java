package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuBoardTest {

    private final SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void getFieldTest() {
        Assertions.assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> sudokuBoard.getField(-1, 4));

        Assertions.assertEquals(sudokuBoard.getField(0, 0), 0);
    }

    @Test
    public void setFieldTest() {
        Assertions.assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> sudokuBoard.setField(-1, 4, 1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.setField(1, 4, -1));

        sudokuBoard.setField(0, 0, 1);
        Assertions.assertEquals(sudokuBoard.getField(0, 0), 1);
    }

    @Test
    public void getColumnTest() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getColumn(-1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getColumn(9));

        Assertions.assertNotEquals(sudokuBoard.getColumn(0).values.size(), 0);

        for (SudokuField f : sudokuBoard.getColumn(0).values) {
            Assertions.assertEquals(f.getFieldValue(), 0);
        }
    }

    @Test
    public void getRowTest() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getRow(-1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sudokuBoard.getRow(9));

        for (SudokuField f : sudokuBoard.getRow(0).values) {
            Assertions.assertEquals(f.getFieldValue(), 0);
        }
    }

    @Test
    public void getBoxTest() {
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

        for (SudokuField f : sudokuBoard.getBox(0, 0).values) {
            Assertions.assertEquals(f.getFieldValue(), 0);
        }
    }

    @Test
    public void equalsTest() {
        Assertions.assertNotEquals(sudokuBoard, null);
        Assertions.assertNotEquals(sudokuBoard, new SudokuField());
        Assertions.assertEquals(sudokuBoard, sudokuBoard);

        sudokuBoard.setField(0, 0, 2);
        SudokuBoard newSudokuBoard = sudokuBoard;
        Assertions.assertEquals(sudokuBoard, newSudokuBoard);

        newSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        Assertions.assertNotEquals(sudokuBoard, newSudokuBoard);

        for (int i = 0; i < Consts.SIZE; i++) {
            for (int j = 0; j < Consts.SIZE; j++) {
                sudokuBoard.setField(i, j, 1);
                newSudokuBoard.setField(i, j, 1);
            }
        }
        Assertions.assertEquals(sudokuBoard, newSudokuBoard);
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard newSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newSudokuBoard.setField(i, j, sudokuBoard.getField(i, j));
            }
        }

        Assertions.assertEquals(sudokuBoard.hashCode(), newSudokuBoard.hashCode());

        newSudokuBoard.setField(0, 0, 9);
        newSudokuBoard.setField(1, 0, 9);
        Assertions.assertNotEquals(sudokuBoard.hashCode(), newSudokuBoard.hashCode());
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals(sudokuBoard.toString(), sudokuBoard.toString());
        Assertions.assertNotEquals(
                sudokuBoard.toString(),
                new SudokuBoard(new BacktrackingSudokuSolver()).toString()
        );
    }
}