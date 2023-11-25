module com.automates.automates {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires com.h2database;
    requires java.sql;
    requires org.hibernate.orm.core;
    opens com.automates.automates to javafx.fxml;
    opens com.automates.automates.Model;
    exports com.automates.automates;
    exports com.automates.automates.component;
}