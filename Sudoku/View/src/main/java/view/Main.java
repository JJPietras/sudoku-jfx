package view;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;
    static ResourceBundle resourceBundle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            Locale locale = new Locale("en", "UK");
            resourceBundle = ResourceBundle.getBundle("textMenu", locale);
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getClassLoader().getResource("main_menu.fxml")),
                    resourceBundle
            );
            primaryStage.setTitle("Sudoku");
            primaryStage.setScene(new Scene(root));
            primaryStage.getScene().getStylesheets().add("main_menu_style.css");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
