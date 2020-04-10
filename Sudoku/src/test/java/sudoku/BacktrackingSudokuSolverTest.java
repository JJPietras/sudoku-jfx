package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

class BacktrackingSudokuSolverTest {
    private final SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private final SudokuBoard boardOne = new SudokuBoard(new BacktrackingSudokuSolver());
    private final SudokuBoard boardTwo = new SudokuBoard(new BacktrackingSudokuSolver());


    @Test
    void solvePrimaryTest() {
        sudokuBoard.solveGame();
        HashSet<Integer> set = new HashSet<>();
        for (int k = 0; k < 2; k++) {
            boolean row = k == 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Assertions.assertFalse(set.contains(sudokuBoard.getField(row ? j : i, row ? i : j)));
                    set.add(sudokuBoard.getField(row ? j : i, row ? i : j));
                }
                set.clear();
            }
        }
        for (int k = 0; k < 3; k++) {
            for (int i = (k * 3); i < 3 * (k + 1); i++) {
                for (int l = 0; l < 3; l++) {
                    for (int j = (l * 3); j < 3 * (l + 1); j++) {
                        set.add(sudokuBoard.getField(j, i));
                    }
                }
                Assertions.assertEquals(9, set.size());
                set.clear();
            }
        }
    }

    @Test
    void boardsDifferencesTest() {
        boardOne.solveGame();
        boardTwo.solveGame();
        int differences = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (boardOne.getField(j, i) != boardTwo.getField(j, i)) {
                    differences++;
                }
            }
        }

        Assertions.assertTrue(differences > 0);
        System.out.println("Boards have " + differences + " different cells");
    }
}