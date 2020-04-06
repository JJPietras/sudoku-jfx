package sudoku;

public class SudokuBoard {

    private final SudokuSolver sudokuSolver;
    private final SudokuField[][] board;

    public SudokuBoard(SudokuSolver solver) {
        board = new SudokuField[Consts.SIZE][Consts.SIZE];
        for (int i = 0; i < Consts.SIZE; i++) {
            for (int j = 0; j < Consts.SIZE; j++) {
                board[i][j] = new SudokuField();
            }
        }
        sudokuSolver = solver;
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public int getField(int x, int y) {
        if (x < 0 || y < 0 || x >= Consts.SIZE || y >= Consts.SIZE) {
            throw new ArrayIndexOutOfBoundsException(Consts.OUT_OF_BOUNDS);
        }
        return board[y][x].getFieldValue();
    }

    public void setField(int x, int y, int value) {
        if (x < 0 || y < 0 || x >= Consts.SIZE || y >= Consts.SIZE) {
            throw new ArrayIndexOutOfBoundsException(Consts.OUT_OF_BOUNDS);
        }
        board[y][x].setFieldValue(value);
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
        return new SudokuRow(board[y]);
    }

    SudokuColumn getColumn(int x) {
        if (x < 0 || x > 8) {
            throw new IllegalArgumentException(Consts.COL_OUT_OF_BOUNDS + " " + x);
        }
        SudokuField[] column = new SudokuField[Consts.SIZE];
        for (int i = 0; i < Consts.SIZE; i++) {
            column[i] = board[i][x];
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

        SudokuField[] box = new SudokuField[Consts.SIZE];
        int k = 0;
        //Get first indexes of box
        int boxY = (y % Consts.BOX_SIZE) * Consts.BOX_SIZE;
        int boxX = (x % Consts.BOX_SIZE) * Consts.BOX_SIZE;
        for (int i = boxY; i < boxY + Consts.BOX_SIZE; i++) {
            for (int j = boxX; j < boxX + Consts.BOX_SIZE; j++) {
                box[k++] = board[i][j];
            }
        }
        return new SudokuBox(box);
    }
}