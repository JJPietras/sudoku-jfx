import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add("main_menu_style.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
