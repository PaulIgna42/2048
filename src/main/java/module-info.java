module com.example.lastminute {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lastminute to javafx.fxml;
    exports com.example.lastminute;
    exports com.example.lastminute.gui;
    opens com.example.lastminute.gui to javafx.fxml;
}