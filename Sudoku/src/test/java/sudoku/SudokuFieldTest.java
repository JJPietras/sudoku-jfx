package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuFieldTest {
    private final SudokuField field = new SudokuField();

    @Test
    public void sudokuFieldExceptionsTest() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> field.setFieldValue(-1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> field.setFieldValue(10));
    }

    @Test
    public void sudokuFieldGetSetTest() {
        field.setFieldValue(5);
        Assertions.assertEquals(5, field.getFieldValue());
    }

    @Test
    public void toStringTest() {
        //System.out.println(field.toString());
    }

    @Test
    public void equalsTest() {
        Assertions.assertFalse(field.equals(null));
        Assertions.assertTrue(field.equals(field));

        SudokuField field1 = field;
        Assertions.assertTrue(field.equals(field1));

        field1 = new SudokuField();
        field.setFieldValue(1);
        Assertions.assertFalse(field.equals(field1));
        field1.setFieldValue(1);
        Assertions.assertTrue(field.equals(field1));
    }
}