package com.automates.automates;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.h2.tools.Server;
import java.io.IOException;
import java.sql.SQLException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws SQLException, IOException {
        //adatbázis elindítása
        startDatabase();
        //online h2 sql manager: http://<IpCímed>:8082 de a console írja, lehet neked más lesz
        //javafx felület
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setResizable(false);
        stage.setTitle("Automates");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws SQLException {
        launch();
    }

    private static void startDatabase() throws SQLException {
        new Server().runTool("-tcp", "-web", "-ifNotExists");
    }
}