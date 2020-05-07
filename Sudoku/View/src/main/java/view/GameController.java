package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sudoku.Difficulty;
import sudoku.GameState;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    private GameState gameState;
    private int i = 0;

    @FXML
    GridPane gridPane;

    @FXML
    TextField difficulty;

    @FXML
    TextField gameName;

    @FXML
    TextField elapsedTime;

    public void quitGameMode() throws IOException {
        new FXMLLoader();
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("main_menu.fxml")
                )
        );
        Main.stage.setScene(new Scene(root));
    }

    public void startup(Difficulty difficulty) {
        gameState = new GameState(difficulty);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setFieldValue(i, j, String.valueOf(gameState.getUserBoard().getField(i, j)));
            }
        }
        this.difficulty.setText(this.difficulty.getText().concat(" " + difficulty.name()));
        gameName.setText(gameName.getText().concat(" " + gameState.getGameName()));
        timerDisplay();
    }

    private void setFieldValue(int x, int y, String value) {
        //TODO: check if out of bounds
        ObservableList<Node> subGrids = gridPane.getChildren();
        ObservableList<Node> boxFields = ((GridPane) subGrids.get((y / 3) * 3 + (x / 3))).getChildren();
        ((TextField) (boxFields.get((y % 3) * 3 + (x % 3)))).setText(!value.equals("0") ? value : "");
    }

    private void timerDisplay() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> elapsedTime.setText("Elapsed Time: " + i++));
            }
        }, 0, 1000);
    }
}