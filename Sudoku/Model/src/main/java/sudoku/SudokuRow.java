package sudoku;

import java.util.List;

public class SudokuRow extends SudokuContainer {

    public SudokuRow(List<SudokuField> values) {
        super(values);
    }

    @Override
    protected SudokuRow clone() throws CloneNotSupportedException {
        return (SudokuRow) super.clone();
    }
}
