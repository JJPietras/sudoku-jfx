package sudoku;

import java.util.List;

public class SudokuBox extends SudokuContainer {

    public SudokuBox(List<SudokuField> values) {
        super(values);
    }

    @Override
    protected SudokuBox clone() throws CloneNotSupportedException {
        return (SudokuBox) super.clone();
    }
}
