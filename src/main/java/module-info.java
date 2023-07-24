module com.example.slar_calculate {
    requires javafx.controls;
    requires javafx.fxml;
    requires matheclipse.core;
    requires matheclipse.frontend;


    opens com.example.slar_calculate to javafx.fxml;
    exports com.example.slar_calculate;
}