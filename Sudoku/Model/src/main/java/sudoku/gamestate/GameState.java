package sudoku.gamestate;

import java.util.Random;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.model.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

public class GameState {

    private final SudokuBoard completeBoard;
    private final SudokuBoard userBoard;
    private final Difficulty difficulty;
    private final String gameName;

    public GameState(Difficulty difficulty) throws
            FieldOutOfBoundsException, InvalidFieldValueException {
        this(difficulty, "Generic Game");
    }

    public GameState(SudokuBoard board, Difficulty difficulty, String gameName) throws
            FieldOutOfBoundsException, InvalidFieldValueException {
        completeBoard = board;
        userBoard = completeBoard.clone();
        this.difficulty = difficulty;
        this.gameName = gameName;

        randomize();
    }

    public GameState(Difficulty difficulty, String gameName) throws
            FieldOutOfBoundsException, InvalidFieldValueException {
        completeBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        completeBoard.solveGame();

        userBoard = completeBoard.clone();
        this.difficulty = difficulty;
        this.gameName = gameName;

        randomize();
    }

    private void randomize() throws FieldOutOfBoundsException, InvalidFieldValueException {
        Random random = new Random();
        int indexX = random.nextInt(9);
        int indexY = random.nextInt(9);

        for (byte i = 0; i < difficulty.getValue(); i++) {
            while (userBoard.getField(indexX, indexY) == 0) {
                indexX = random.nextInt(9);
                indexY = random.nextInt(9);
            }
            userBoard.setField(indexX, indexY, 0);
        }
    }

    public boolean compareFields(int x, int y) throws FieldOutOfBoundsException {
        return completeBoard.getField(x, y) == userBoard.getField(x, y);
    }

    public SudokuBoard getCompleteBoard() {
        return completeBoard;
    }

    public SudokuBoard getUserBoard() {
        return userBoard;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getGameName() {
        return gameName;
    }
}
