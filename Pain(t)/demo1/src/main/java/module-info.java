module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires javafx.swing;
    requires org.junit.jupiter.api;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
}