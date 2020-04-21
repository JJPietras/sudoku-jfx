package sudoku;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public abstract class SudokuContainer {

    protected final List<SudokuField> values;
    private final HashSet<SudokuField> set = new HashSet<>();

    public SudokuContainer(List<SudokuField> values) {
        if (values.size() != Consts.SIZE) {
            throw new IllegalArgumentException(Consts.INVALID_LENGTH);
        }
        for (SudokuField field : values) {
            Objects.requireNonNull(field, Consts.NULL_ELEMENT);
        }

        this.values = Objects.requireNonNull(values, Consts.NULL_ARRAY);
    }

    public boolean verify() {
        set.clear();
        set.addAll(values);
        return set.size() == Consts.SIZE;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("values", values)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuContainer that = (SudokuContainer) o;

        return new EqualsBuilder()
                .append(values, that.values)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 41)
                .append(values)
                .toHashCode();
    }
}
