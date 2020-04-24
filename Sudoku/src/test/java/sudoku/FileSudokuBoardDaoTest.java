package sudoku;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

class FileSudokuBoardDaoTest {
    private final static String fileName = "SavedBoard.board";
    private final static SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    private final Dao<SudokuBoard> fileSudokuBoardDao =
            new SudokuBoardDaoFactory().getFileDao(fileName);

    @BeforeAll
    static void initialize() {
        board.solveGame();
    }

    @Test
    void writeTest() {
        fileSudokuBoardDao.write(board);
        File file = new File(fileName);
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.length() > 0);
    }

    @Test
    void readTest() {
        SudokuBoard loadedBoard = fileSudokuBoardDao.read();
        Assertions.assertNotNull(loadedBoard);
        Assertions.assertEquals(loadedBoard, board);
    }

    @Test
    void closeTest() throws Exception {
        try (FileSudokuBoardDao ignored = new FileSudokuBoardDao(fileName)) {
            Assertions.assertTrue(true);
        }
    }

    @AfterAll
    static void cleanup() {
        File file = new File(fileName);
        if (file.exists()) {
            Assertions.assertTrue(file.delete());
        }
    }
}