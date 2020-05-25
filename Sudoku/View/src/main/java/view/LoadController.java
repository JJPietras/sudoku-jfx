package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class LoadController implements Initializable {
    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
