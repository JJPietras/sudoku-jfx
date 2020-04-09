package sudoku;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public abstract class SudokuContainer {

    private final HashSet<SudokuField> set = new HashSet<>();
    protected final List<SudokuField> values;

    public SudokuContainer(List<SudokuField> values) {
        if (values.size() != Consts.SIZE) {
            throw new IllegalArgumentException(Consts.INVALID_LENGTH);
        }
        for (SudokuField field : values) {
            Objects.requireNonNull(field, Consts.NULL_ELEMENT);
        }
        Objects.requireNonNull(values, Consts.NULL_ARRAY);
        this.values = values;
    }

    public boolean verify() {
        set.clear();
        set.addAll(values);
        return set.size() == Consts.SIZE;
    }
}
