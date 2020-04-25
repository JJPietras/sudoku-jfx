package sudoku;

public class SudokuBoardDaoFactory {
    Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
