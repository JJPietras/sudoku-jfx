package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;

class GameStateTest {

    @Test
    public void GameStateConstructionTest() throws FieldOutOfBoundsException, InvalidFieldValueException {
        GameState gameState = new GameState(Difficulty.NORMAL);
        byte zerosCount = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameState.getUserBoard().getField(i, j) == 0) {
                    zerosCount++;
                }
            }
        }
        Assertions.assertEquals(zerosCount, Difficulty.NORMAL.getValue());
    }

}