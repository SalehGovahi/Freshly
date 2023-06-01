module com.example.demo67 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.demo67 to javafx.fxml;
    exports com.example.demo67;
}