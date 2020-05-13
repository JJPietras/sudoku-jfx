package view;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import sudoku.BacktrackingSudokuSolver;
import sudoku.Difficulty;
import sudoku.GameState;
import sudoku.SudokuBoard;

public class GameController implements Initializable {

    private final EventHandler<KeyEvent> onFieldInput = keyEvent -> {
        String input = keyEvent.getCharacter();
        TextField field = (TextField) keyEvent.getTarget();
        if (!input.matches("[1-9]") && !input.equals("") || field.getText().length() > 1) {
            //keyEvent.getCharacter().replace(keyEvent);
            if (input.matches("[1-9]")) {
                field.setText(input);
            } else {
                field.setText("");
            }
        }
    };

    ResourceBundle resourceBundle;

    SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    @FXML
    Button enLang, plLang;

    @FXML
    Button solveBt, checkBt;

    @FXML
    Button newBoardBt, loadBoardBt, saveBoardBt;

    @FXML
    Button quitBt;

    @FXML
    GridPane gridPane;

    @FXML
    TextField difficulty;

    @FXML
    TextField gameName;

    @FXML
    TextField elapsedTime;

    @FXML
    TextField authors;

    private GameState gameState;
    private int i = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage("pl");
        resourceBundle = this.resourceBundle;
        checkBt.setText(resourceBundle.getString("CheckButton"));
        //solveBt.setText(resourceBundle.getString("SolveButton"));
        newBoardBt.setText(resourceBundle.getString("NewBoardButton"));
        loadBoardBt.setText(resourceBundle.getString("LoadBoardButton"));
        saveBoardBt.setText(resourceBundle.getString("SaveBoardButton"));
        //quitBt.setText(resourceBundle.getString("QuitButton"));
        authorsDisplay();
    }

    public void quitGameMode() throws IOException {
        new FXMLLoader();
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("main_menu.fxml")
                )
        );

        Main.stage.setScene(new Scene(root));
    }

    private void setLanguage(String language) {
        Locale.setDefault(new Locale(language));
        this.resourceBundle = ResourceBundle.getBundle("textGame", Locale.getDefault());

        Button newLanguage;
        Button prevLanguage;
        switch (language) {
            case "pl":
                newLanguage = plLang;
                prevLanguage = enLang;
                break;
            case "en":
                newLanguage = enLang;
                prevLanguage = plLang;
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected value: " + language
                );
        }

        prevLanguage.setEffect(new Blend());
        newLanguage.setEffect(new DropShadow());
        newLanguage.setEffect(new SepiaTone());
    }

    public void startup(Difficulty difficulty) {
        gameState = new GameState(difficulty);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setFieldValue(i, j, String.valueOf(gameState.getUserBoard().getField(i, j)));
                setFieldValidator(i, j, onFieldInput);
            }
        }
        this.difficulty.setText(resourceBundle.getString("DifficultyLabel").concat(difficulty.name()));
        gameName.setText(resourceBundle.getString("GameNameLabel").concat(gameState.getGameName()));
        timerDisplay();
    }

    private TextField getField(int x, int y) {
        ObservableList<Node> subGrids = gridPane.getChildren();
        ObservableList<Node> boxFields = ((GridPane) subGrids.get((y / 3) * 3 + (x / 3))).getChildren();
        return (TextField) (boxFields.get((y % 3) * 3 + (x % 3)));
    }

    private void setFieldValue(int x, int y, String value) {
        getField(x, y).setText(!value.equals("0") ? value : "");
    }

    private void setFieldValidator(int x, int y, EventHandler<KeyEvent> validator) {
        getField(x, y).setOnKeyTyped(validator);
    }

    private void authorsDisplay() {
        ResourceBundle authorsList = ResourceBundle.getBundle("view.Authors", Locale.getDefault());
        authors.setText(resourceBundle.getString("AuthorsLabel").concat(
                authorsList.getObject("1") + " & " + authorsList.getObject("2")));
    }

    private void timerDisplay() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> elapsedTime.setText(resourceBundle.getString("TimeLabel") + i++));
            }
        }, 0, 1000);
    }
}