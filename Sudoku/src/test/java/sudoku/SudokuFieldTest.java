package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuFieldTest {
    private final SudokuField field = new SudokuField();

    @Test
    void sudokuFieldExceptionsTest() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> field.setFieldValue(-1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> field.setFieldValue(10));
    }

    @Test
    void sudokuFieldGetSetTest() {
        field.setFieldValue(5);
        Assertions.assertEquals(5, field.getFieldValue());
    }
}