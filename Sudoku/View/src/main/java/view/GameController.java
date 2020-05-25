package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import sudoku.Dao;
import sudoku.Difficulty;
import sudoku.GameState;
import sudoku.SudokuBoard;
import sudoku.SudokuBoardDaoFactory;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.exceptions.ReadBoardException;
import sudoku.exceptions.WriteBoardException;

public class GameController implements Initializable {

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

    private int i = 0;

    private ResourceBundle resourceBundle;

    private GameState gameState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        addCheckingToFields();
        authorsDisplay();
    }

    public void quitGameMode() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("textMenu", resourceBundle.getLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Scenes/main_menu.fxml"), bundle);
        Parent root = loader.load();
        Main.stage.setScene(new Scene(root));
        Main.logger.info("Game closed");
    }

    public void startup(Difficulty difficulty) {
        Main.logger.info("Created new game, difficulty: " + difficulty.toString());
        try {
            gameState = new GameState(difficulty);
        } catch (FieldOutOfBoundsException | InvalidFieldValueException e) {
            Main.logger.error("Error creating new GameState - invalid parameters");
            e.printStackTrace();
        }
        displayGame();
        this.difficulty.setText(resourceBundle.getString("DifficultyLabel").concat(difficulty.name()));
        gameName.setText(resourceBundle.getString("GameNameLabel").concat(gameState.getGameName()));
        timerDisplay();
    }

    private void displayGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    setFieldValue(j, i, String.valueOf(gameState.getUserBoard().getField(j, i)));
                } catch (FieldOutOfBoundsException e) {
                    Main.logger.error("Field indexes are wrong");
                    e.printStackTrace();
                }
            }
        }
    }

    private void addCheckingToFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                getField(j, i).setOnKeyReleased(this::checkInputText);
                setFieldValidator(j, i, onFieldInput);
            }
        }
    }

    public void newGame() {
        try {
            gameState = new GameState(gameState.getDifficulty(), "Sudoku");
        } catch (FieldOutOfBoundsException | InvalidFieldValueException e) {
            Main.logger.error("Error creating new GameState - invalid parameters");
            e.printStackTrace();
        }
        Main.logger.info("Created new game");
        displayGame();
    }

    private final EventHandler<KeyEvent> onFieldInput = keyEvent -> {
        String input = keyEvent.getCharacter();
        TextField field = (TextField) keyEvent.getTarget();
        if (!input.matches("[1-9]") && !input.equals("") || field.getText().length() > 1) {
            if (input.matches("[1-9]")) {
                field.setText(input);
            } else {
                field.setText("");
            }
        }
    };

    @FXML
    private void checkInputText(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        String value = textField.getText();
        textField.setStyle("-fx-text-fill: black;");
        if (value.length() == 1) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                textField.setText("");
            }
        } else {
            textField.setText("");
        }
    }

    public void verify() throws FieldOutOfBoundsException, InvalidFieldValueException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!getField(j, i).getText().equals("")) {
                    gameState.getUserBoard().setField(j, i, Integer.parseInt(getField(j, i).getText()));
                }
                getField(j, i).setStyle("-fx-text-fill: black;");
                if (!getField(j, i).getText().equals("") && !gameState.compareFields(j, i)) {
                    getField(j, i).setStyle("-fx-text-fill: red;");
                }
            }
        }
    }

    public void solve() throws FieldOutOfBoundsException {
        SudokuBoard board = gameState.getCompleteBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                getField(j, i).setStyle("-fx-text-fill: black;");
                getField(j, i).setText(Integer.toString(board.getField(j, i)));
            }
        }
        Main.logger.info("Performed solve operation");
    }

    public void resetStyle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                getField(j, i).setStyle("-fx-text-fill: black;");
            }
        }
    }

    public void saveBoard() {
        SudokuBoard sudokuBoard = gameState.getCompleteBoard();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(Main.stage);
        if (file != null) {
            Dao<SudokuBoard> fileSudokuBoardDao = new SudokuBoardDaoFactory().getFileDao(file.getAbsolutePath());
            try {
                fileSudokuBoardDao.write(sudokuBoard);
            } catch (WriteBoardException e) {
                Main.logger.error("Could not save board to selected file");
                e.printStackTrace();
            }
        }
    }

    public void loadBoard() {
        resetStyle();

        ResourceBundle bundle = ResourceBundle.getBundle("textLoad", resourceBundle.getLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Scenes/load_menu.fxml"), bundle);

        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
            stage.show();

        } catch (IOException exception) {
            exception.printStackTrace();
        }


        /*FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Main.stage);
        if (file != null) {
            Dao<SudokuBoard> fileSudokuBoardDao = new SudokuBoardDaoFactory().getFileDao(file.getAbsolutePath());
            SudokuBoard board = null;
            try {
                board = fileSudokuBoardDao.read();
            } catch (ReadBoardException e) {
                Main.logger.error("Could not read selected file");
                e.printStackTrace();
            }
            try {
                gameState = new GameState(board, gameState.getDifficulty(), gameState.getGameName());
            } catch (FieldOutOfBoundsException | InvalidFieldValueException e) {
                Main.logger.error("Error creating new GameState - invalid parameters");
                e.printStackTrace();
            }
            displayGame();
        } else {
            Main.logger.error("Wrong file or none selected");
        }*/
    }

    private TextField getField(int x, int y) {
        ObservableList<Node> subGrids = gridPane.getChildren();
        ObservableList<Node> boxFields = ((GridPane) subGrids.get(((y / 3) * 3) + (x / 3))).getChildren();
        return (TextField) (boxFields.get(((y % 3) * 3) + (x % 3)));
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
                SwingUtilities.invokeLater(
                        () -> elapsedTime.setText(resourceBundle.getString("TimeLabel") + i++)
                );
            }
        }, 0, 1000);
    }
}