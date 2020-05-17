package view;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import sudoku.Difficulty;

public class MenuController implements Initializable {

    @FXML
    Button enLang;

    @FXML
    Button plLang;

    @FXML
    private ToggleGroup difficultyGroup;

    private ResourceBundle resourceBundle;

    @FXML
    private final EventHandler<MouseEvent> onLanguageSelect = actionEvent -> {
        String lang = ((Button) actionEvent.getSource()).getText();
        try {
            setLanguage(lang.toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        plLang.setOnMouseClicked(onLanguageSelect);
        enLang.setOnMouseClicked(onLanguageSelect);
    }

    public void startGame() throws IOException {
        Main.logger.info("Starting new game");
        ResourceBundle bundle = ResourceBundle.getBundle("textGame", resourceBundle.getLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("game.fxml"), bundle);
        Difficulty difficulty;
        String level = ((RadioButton) difficultyGroup.getSelectedToggle()).getText();
        if (level.equals((resourceBundle.getString("EasyRadioButton")))) {
            difficulty = Difficulty.EASY;
        } else if (level.equals((resourceBundle.getString("NormalRadioButton")))) {
            difficulty = Difficulty.NORMAL;
        } else if (level.equals((resourceBundle.getString("HardRadioButton")))) {
            difficulty = Difficulty.HARD;
        } else {
            Main.logger.error("Invalid difficulty state");
            throw new IllegalStateException(
                    "Unexpected value: " + difficultyGroup.getSelectedToggle().toString()
            );
        }
        Parent root = loader.load();
        GameController gameController = loader.getController();
        gameController.startup(difficulty);
        Main.stage.setScene(new Scene(root));
    }

    private void setLanguage(String language) throws IOException {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        resourceBundle = ResourceBundle.getBundle("textMenu", locale);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"), resourceBundle);
        Parent root = loader.load();
        Main.stage.setScene(new Scene(root));
    }

    public void quitApplication() {
        Main.logger.info("Exiting application");
        Platform.exit();
        System.exit(0);
    }
}
