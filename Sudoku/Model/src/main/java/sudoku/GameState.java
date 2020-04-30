package sudoku;

import java.util.Random;
import lombok.Data;

@Data
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

}
