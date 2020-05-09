package sudoku;

import java.util.Objects;
import java.util.Random;

public class GameState {

    private SudokuBoard completeBoard;
    private SudokuBoard userBoard;
    private Difficulty difficulty;
    private String gameName;

    public GameState(Difficulty difficulty) {
        this(difficulty, "Generic Game");
    }

    public GameState(Difficulty difficulty, String gameName) {
        completeBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        completeBoard.solveGame();
        userBoard = completeBoard.clone();
        this.difficulty = difficulty;
        this.gameName = gameName;

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

    public SudokuBoard getCompleteBoard() {
        return completeBoard;
    }

    public void setCompleteBoard(SudokuBoard completeBoard) {
        this.completeBoard = Objects.requireNonNull(completeBoard);
    }

    public SudokuBoard getUserBoard() {
        return userBoard;
    }

    public void setUserBoard(SudokuBoard userBoard) {
        this.userBoard = Objects.requireNonNull(userBoard);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getGameName() {
        return gameName;
    }
}
