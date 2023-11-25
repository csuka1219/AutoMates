package com.automates.automates;

import com.automates.automates.Data.UserData;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.UserDAO;
import com.automates.automates.repositories.JpaUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {
    @FXML
    private Text usernameText;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button logoutButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameText.setText(UserData.getName());

        button1.setOnAction(event -> {
            try {
                navigateToMyCars();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button2.setOnAction(event -> {
            try {
                navigateToStatictics();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        logoutButton.setOnAction(event -> logout());

        User user = new User();
        try (UserDAO uDAO = new JpaUserDAO()) {
            user = uDAO.GetUserById(UserData.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!user.isProvider()){
            button1.setText("Főoldal");
            button2.setText("Bérléseim");
            button1.setOnAction(event -> {
                try {
                    navigateToMainPage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            button2.setOnAction(event -> {
                try {
                    navigateToMyRents();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void navigateToMyCars() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mycars-view.fxml"));
        Stage stage = (Stage) button1.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
    private void navigateToStatictics() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("statisztikak.fxml"));
        Stage stage = (Stage) button2.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
    private void navigateToMainPage() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("főoldal.fxml"));
        Stage stage = (Stage) button1.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
    private void navigateToMyRents() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("berleseim.fxml"));
        Stage stage = (Stage) button2.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
    private void logout(){
        //later
    }
}
