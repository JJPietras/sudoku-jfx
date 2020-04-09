package sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SudokuBoard {

    private final SudokuSolver sudokuSolver;
    private final List<List<SudokuField>> board;

    public SudokuBoard(SudokuSolver solver) {
        @SuppressWarnings("unchecked")
        List<SudokuField>[] array = new List[Consts.SIZE];
        SudokuField[] fields = new SudokuField[Consts.SIZE];
        for (int i = 0; i < Consts.SIZE; i++) {
            for (int j = 0; j < Consts.SIZE; j++) {
                fields[j] = new SudokuField();
            }
            array[i] = Arrays.asList(fields);
        }
        board = Arrays.asList(array);
        sudokuSolver = Objects.requireNonNull(solver);
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public int getField(int x, int y) {
        if (x < 0 || y < 0 || x >= Consts.SIZE || y >= Consts.SIZE) {
            throw new ArrayIndexOutOfBoundsException(Consts.OUT_OF_BOUNDS);
        }
        return board.get(y).get(x).getFieldValue();
    }

    public void setField(int x, int y, int value) {
        if (x < 0 || y < 0 || x >= Consts.SIZE || y >= Consts.SIZE) {
            throw new ArrayIndexOutOfBoundsException(Consts.OUT_OF_BOUNDS);
        }
        board.get(y).get(x).setFieldValue(value);
    }

    private boolean checkBoard() {
        for (int i = 0; i < Consts.SIZE; i++) {
            if (!(getRow(i).verify() || getColumn(i).verify())) {
                return false;
            }
        }
        for (int i = 0; i < Consts.SIZE; i += Consts.BOX_SIZE) {
            for (int j = 0; j < Consts.SIZE; j += Consts.BOX_SIZE) {
                if (!getBox(i, j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    SudokuRow getRow(int y) {
        if (y < 0 || y > 8) {
            throw new IllegalArgumentException(Consts.ROW_OUT_OF_BOUNDS + y);
        }
        return new SudokuRow(board.get(y));
    }

    SudokuColumn getColumn(int x) {
        if (x < 0 || x > 8) {
            throw new IllegalArgumentException(Consts.COL_OUT_OF_BOUNDS + " " + x);
        }
        List<SudokuField> column = Arrays.asList(new SudokuField[Consts.SIZE]);
        for (int i = 0; i < Consts.SIZE; i++) {
            column.set(i, board.get(i).get(x));
        }
        return new SudokuColumn(column);
    }

    SudokuBox getBox(int x, int y) {
        if (x < 0 || x >= Consts.SIZE) {
            throw new IllegalArgumentException(Consts.COL_OUT_OF_BOUNDS + " " + x);
        }
        if (y < 0 || y >= Consts.SIZE) {
            throw new IllegalArgumentException(Consts.ROW_OUT_OF_BOUNDS + " " + y);
        }

        List<SudokuField> box = Arrays.asList(new SudokuField[Consts.SIZE]);
        int k = 0;
        //Get first indexes of box
        int boxY = (y % Consts.BOX_SIZE) * Consts.BOX_SIZE;
        int boxX = (x % Consts.BOX_SIZE) * Consts.BOX_SIZE;
        for (int i = boxY; i < boxY + Consts.BOX_SIZE; i++) {
            for (int j = boxX; j < boxX + Consts.BOX_SIZE; j++) {
                box.set(k++, board.get(i).get(j));
            }
        }
        return new SudokuBox(box);
    }
}