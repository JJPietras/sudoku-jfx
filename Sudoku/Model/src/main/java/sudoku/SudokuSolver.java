package sudoku;

import java.io.Serializable;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;

public interface SudokuSolver extends Serializable {
    void solve(SudokuBoard board) throws FieldOutOfBoundsException, InvalidFieldValueException;
}
