package view;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Blend;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.input.MouseEvent;
import sudoku.Difficulty;

public class MenuController implements Initializable {

    ResourceBundle resourceBundle;

    @FXML
    Button enLang, plLang;

    @FXML
    private final EventHandler<MouseEvent> onLanguageSelect = actionEvent -> {
        String lang = ((Button) actionEvent.getSource()).getText();
        try {
            setLanguage(lang.toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    @FXML
    private TextField difficultyLabel;

    @FXML
    private ToggleGroup difficultyGroup;

    @FXML
    private RadioButton easyRadioBt, normalRadioBt, hardRadioBt;

    @FXML
    private Button startGameBt, quitGameBt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setLanguage(Locale.getDefault().getLanguage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        resourceBundle = this.resourceBundle;
        difficultyLabel.setText(resourceBundle.getString("DifficultyLabel"));
        easyRadioBt.setText(resourceBundle.getString("EasyRadioButton"));
        normalRadioBt.setText(resourceBundle.getString("NormalRadioButton"));
        hardRadioBt.setText(resourceBundle.getString("HardRadioButton"));
        startGameBt.setText(resourceBundle.getString("StartButton"));
        quitGameBt.setText(resourceBundle.getString("QuitButton"));
        plLang.setOnMouseClicked(onLanguageSelect);
        enLang.setOnMouseClicked(onLanguageSelect);
    }

    public void reload() throws IOException {
        new FXMLLoader();
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("main_menu.fxml")
                )
        );

        Main.stage.setScene(new Scene(root));
    }

    public void startGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("game.fxml"));
        Parent root = loader.load();
        GameController gameController = loader.getController();

        Difficulty difficulty;
        String level = ((RadioButton) difficultyGroup.getSelectedToggle()).getText();

//        if (level.equals(resourceBundle.getString("EasyRadioButton"))) {
//            difficulty = Difficulty.EASY;
//        } else if (level.equals(resourceBundle.getString("NormalRadioButton"))) {
//            difficulty = Difficulty.NORMAL;
//        } else if (level.equals(resourceBundle.getString("HardRadioButton"))) {
//            difficulty = Difficulty.HARD;
//        } else {
//            throw new IllegalStateException(
//                    "Unexpected value: " + difficultyGroup.getSelectedToggle().toString()
//            );
//        }
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

    private void setLanguage(String language) throws IOException {
        Locale.setDefault(new Locale(language));
        this.resourceBundle = ResourceBundle.getBundle("textMenu", Locale.getDefault());

        switch (language) {
            case "pl":
                enLang.setEffect(new Blend());
                plLang.setEffect(new DropShadow());
                plLang.setEffect(new SepiaTone());
                break;
            case "en":
                plLang.setEffect(new Blend());
                enLang.setEffect(new DropShadow());
                enLang.setEffect(new SepiaTone());
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected value: " + language
                );
        }
//        Main.stage.close();
//        Main.stage.show();
        reload();
    }

    public void quitApplication() {
        Platform.exit();
        System.exit(0);
    }
}
