package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import sudoku.Difficulty;
import java.io.IOException;

public class MenuController {

    @FXML
    private ToggleGroup difficultyGroup;

    public void startGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("game.fxml"));
        Parent root = loader.load();
        GameController gameController = loader.getController();

        Difficulty difficulty;
        switch (((RadioButton)difficultyGroup.getSelectedToggle()).getText()) {
            case "Easy":
                difficulty = Difficulty.EASY;
                break;
            case "Normal":
                difficulty = Difficulty.NORMAL;
                break;
            case "Hard":
                difficulty = Difficulty.HARD;
                break;
            default:
                throw new IllegalStateException(
                        "Unexpected value: " + difficultyGroup.getSelectedToggle().toString()
                );
        }
        gameController.startup(difficulty);
        Main.stage.setScene(new Scene(root));
    }

    public void quitApplication() {
        Platform.exit();
        System.exit(0);
    }
}
