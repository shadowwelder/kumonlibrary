module com.library.kumonlibrary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires com.jfoenix;
    //requires org.apache.poi.poi;
    requires java.desktop;
    requires org.apache.poi.ooxml;


    opens com.library.kumonlibrary to javafx.fxml;
    exports com.library.kumonlibrary;
}