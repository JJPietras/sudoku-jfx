module View {
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires Model;

    opens view;
    exports view;
}