module com.example.tareaad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.tareaad to javafx.fxml;
    exports com.example.tareaad;
}