package view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.Dao;
import sudoku.SudokuBoard;
import sudoku.SudokuBoardDaoFactory;
import sudoku.exceptions.ReadBoardException;

public class LoadController implements Initializable {
    private GameController gameController;

    @FXML
    private TextField pathTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                pathTextField.setText("PLACEHOLDER");
            } catch (ReadBoardException exception) {
                Main.logger.error("Could not read selected file");
                exception.printStackTrace();
            }
            gameController.updateGameState(board);
            gameController.displayGame();

        } else {
            pathTextField.setText("PLACEHOLDER");
        }
    }

    @FXML
    private void returnToGame(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage thisStage = (Stage) source.getScene().getWindow();
        thisStage.close();
    }
}
