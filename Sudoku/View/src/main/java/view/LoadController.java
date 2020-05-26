package view;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.database.Board;
import sudoku.database.DatabaseManager;
import sudoku.dao.Dao;
import sudoku.model.SudokuBoard;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.exceptions.ReadBoardException;

public class LoadController implements Initializable {
    private GameController gameController;

    @FXML
    private TextField pathTextField;

    @FXML
    private TextField status;

    @FXML
    private ListView<String> listView;

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void startup(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    private void selectBoardFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Main.stage);
        if (file != null) {
            pathTextField.setText(file.getAbsolutePath());
            status.setText(resourceBundle.getString("file_selected"));
        } else {
            Main.logger.error("Wrong file or none selected");
        }
    }

    @FXML
    private void loadBoardFromFile() {
        File file = new File(pathTextField.getText());
        if (file.exists()) {
            String path = pathTextField.getText();
            Dao<SudokuBoard> fileSudokuBoardDao = new SudokuBoardDaoFactory().getFileDao(path);
            SudokuBoard board = null;
            try {
                board = fileSudokuBoardDao.read();
                status.setText(resourceBundle.getString("board_file_loaded"));
            } catch (ReadBoardException exception) {
                Main.logger.error("Could not read selected file");
                exception.printStackTrace();
            }
            gameController.updateGameState(board);
            gameController.displayGame();

        } else {
            status.setText(resourceBundle.getString("board_file_failed"));
        }
    }

    @FXML
    private void displayDatabaseEntries() {
        DatabaseManager databaseManager = new DatabaseManager();
        List<Board> boards = databaseManager.getAllEntries();
        databaseManager.releaseResources();
        ObservableList<String> results = FXCollections.observableArrayList();

        for (Board board : boards) {
            results.add(board.getId().toString() + " " + board.getName());
        }
        listView.setItems(results);

        if (results.isEmpty()) {
            status.setText(resourceBundle.getString("no_results"));
        } else {
            status.setText(resourceBundle.getString("finished"));
        }
    }

    @FXML
    private void returnToGame(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage thisStage = (Stage) source.getScene().getWindow();
        thisStage.close();
    }
}