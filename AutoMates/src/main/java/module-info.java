module com.automates.automates {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.automates.automates to javafx.fxml;
    exports com.automates.automates;
}