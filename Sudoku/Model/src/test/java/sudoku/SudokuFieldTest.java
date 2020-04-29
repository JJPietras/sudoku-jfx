package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuFieldTest {
    private SudokuField field = new SudokuField();
    private SudokuField newField = field;

    @Test
    public void getFieldValueTest() {
        Assertions.assertEquals(field.getFieldValue(), 0);
    }

    @Test
    public void setFieldValueTest() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> field.setFieldValue(-1));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> field.setFieldValue(10));

        field.setFieldValue(5);
        Assertions.assertEquals(5, field.getFieldValue());
    }

    @Test
    public void equalsTest() {
        Assertions.assertNotEquals(field, null);
        Assertions.assertEquals(field, field);

        Assertions.assertEquals(field, newField);

        newField = new SudokuField();
        field.setFieldValue(1);
        Assertions.assertNotEquals(field, newField);

        newField.setFieldValue(1);
        Assertions.assertEquals(field, newField);
    }

    @Test
    public void hashCodeTest() {
        Assertions.assertEquals(field.hashCode(), newField.hashCode());

        field = new SudokuField();
        field.setFieldValue(9);
        newField.setFieldValue(9);
        Assertions.assertEquals(field.hashCode(), newField.hashCode());

        field.setFieldValue(8);
        Assertions.assertNotEquals(field.hashCode(), newField.hashCode());
    }

    @Test
    public void toStringTest() {
        newField = new SudokuField();
        Assertions.assertEquals(field.toString(), field.toString());
        Assertions.assertNotEquals(field.toString(), newField.toString());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        newField.setFieldValue(7);
        SudokuField testerField = newField.clone();
        testerField.setFieldValue(4);
        Assertions.assertTrue(testerField.getFieldValue() != newField.getFieldValue());
    }

    @Test
    public void compareToTest() {
        newField.setFieldValue(5);
        SudokuField testerField = new SudokuField();
        testerField.setFieldValue(4);
        Assertions.assertEquals(newField.compareTo(testerField), 1);
        testerField.setFieldValue(5);
        Assertions.assertEquals(newField.compareTo(testerField), 0);
        testerField.setFieldValue(6);
        Assertions.assertEquals(newField.compareTo(testerField), -1);
    }
}