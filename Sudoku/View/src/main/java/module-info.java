module View {
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.apache.logging.log4j;
    requires Model;
    requires org.apache.logging.log4j.core;

    opens view;
    exports view;
}