package sudoku;

import java.util.ArrayList;
import java.util.Collections;

public class BacktrackingSudokuSolver implements SudokuSolver {

    private final ArrayList<Integer> values = makeList();

    private ArrayList<Integer> makeList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= Consts.SIZE; i++) {
            list.add(i);
        }
        return list;
    }

    private boolean isNumberValid(SudokuBoard board, int x, int y, int value) {
        for (int i = 0; i < Consts.SIZE; i++) {
            if (board.getField(i, y) == value || board.getField(x, i) == value) {
                return false;
            }
        }

        int sectionSize = (int) Math.sqrt(Consts.SIZE);
        int sectionFirstRow = x - x % sectionSize;
        int sectionFirstCol = y - y % sectionSize;

        for (int i = sectionFirstRow; i < sectionFirstRow + sectionSize; i++) {
            for (int j = sectionFirstCol; j < sectionFirstCol + sectionSize; j++) {
                if (board.getField(i, j) == value) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean backtrackingAlgorithm(SudokuBoard board, int x, int y) {
        boolean valid = false;

        Collections.shuffle(values);
        for (int i = 0; i < values.size() && !valid; i++) {
            if (isNumberValid(board, x, y, values.get(i))) {
                board.setField(x, y, values.get(i));
                if (x == Consts.SIZE - 1) {
                    if (y == Consts.SIZE - 1) {
                        return true;
                    }
                    valid = backtrackingAlgorithm(board, 0, y + 1);
                } else {
                    valid = backtrackingAlgorithm(board, x + 1, y);
                }
            }
        }
        if (!valid) {
            board.setField(x, y, Consts.UNINITIALIZED);
            return false;
        }
        return true;
    }

    @Override
    public void solve(SudokuBoard board) {
        if (board.getField(0, 0) != Consts.UNINITIALIZED) {
            for (int i = 0; i < Consts.SIZE; i++) {
                for (int j = 0; j < Consts.SIZE; j++) {
                    board.setField(j, i, Consts.UNINITIALIZED);
                }
            }
        }
        backtrackingAlgorithm(board, 0, 0);
    }
}