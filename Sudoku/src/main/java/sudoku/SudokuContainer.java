package sudoku;

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
}
