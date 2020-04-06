package sudoku;

import java.util.Arrays;
import java.util.HashSet;
import org.apache.commons.lang.NullArgumentException;

public abstract class SudokuContainer {

    private final HashSet<SudokuField> set = new HashSet<>();
    protected final SudokuField[] values;

    public SudokuContainer(SudokuField[] values) {
        if (values == null) {
            throw new NullArgumentException(Consts.NULL_ARRAY);
        }
        if (values.length != Consts.SIZE) {
            throw new IllegalArgumentException(Consts.INVALID_LENGTH);
        }
        for (SudokuField field : values) {
            if (field == null) {
                throw new NullArgumentException(Consts.NULL_ELEMENT);
            }
        }
        this.values = values;
    }

    public boolean verify() {
        set.clear();
        set.addAll(Arrays.asList(values));
        return set.size() == Consts.SIZE;
    }
}
