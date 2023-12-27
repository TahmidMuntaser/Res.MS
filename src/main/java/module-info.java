module com.example.rms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.rms to javafx.fxml;
    exports com.example.rms;
}