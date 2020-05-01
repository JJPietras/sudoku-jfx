package sudoku;

import java.util.List;

public class SudokuColumn extends SudokuContainer {
    public SudokuColumn(List<SudokuField> values) {
        super(values);
    }

    @Override
    protected SudokuColumn clone() throws CloneNotSupportedException {
        return (SudokuColumn) super.clone();
    }
}
