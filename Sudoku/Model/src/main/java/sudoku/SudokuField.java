package sudoku;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value < 0 || value > Consts.MAX_VALUE) {
            throw new IllegalArgumentException(Consts.INVALID_VALUE);
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 31)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    protected SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }

    @Override
    public int compareTo(SudokuField o) {
        return Integer.compare(this.getFieldValue(), o.getFieldValue());
    }
}