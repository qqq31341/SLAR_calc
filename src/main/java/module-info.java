module classes.slar {
    requires javafx.controls;
    requires javafx.fxml;
    requires matheclipse.core;
    requires matheclipse.frontend;
    requires org.apache.poi.ooxml;


    opens classes.slar to javafx.fxml;
    exports classes.slar;
}