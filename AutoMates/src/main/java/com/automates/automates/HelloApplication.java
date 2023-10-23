package com.automates.automates;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.UserDAO;
import com.automates.automates.repositories.JpaUserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.h2.tools.Server;
import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws SQLException, IOException {
        //adatbázis elindítása
        startDatabase();
        //online h2 sql manager: http://<IpCímed>:8082 de a console írja, lehet neked más lesz a port.

        //Ez csak egy példa hogy hogy lehet használni.
        try (UserDAO uDAO = new JpaUserDAO();) {
            User user = new User();
            user.setUsername("asd");
            user.setPassword("qwe");
            user.setProvider(false);
            boolean a = uDAO.Register(user);
            boolean d = uDAO.IsUsernameAlreadyExist(user.getUsername());
            boolean e = uDAO.IsUsernameAlreadyExist("abc");
            boolean b = uDAO.Login(user);
            user.setUsername("asd2");
            boolean f = uDAO.Login(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //javafx felület
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
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