package com.automates.automates;

import com.automates.automates.Data.UserData;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.UserDAO;
import com.automates.automates.repositories.JpaUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private Button registButton;
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(event -> handleLoginButton());
        registButton.setOnAction(event -> handleRegistButton());
    }

    private void handleRegistButton() {
        navigateToRegisterPage();
    }

    private void handleLoginButton() {
        login();
    }

    private void login(){
        try (UserDAO uDAO = new JpaUserDAO();) {
            User user = new User();
            user.setUsername(usernameField.getText());
            user.setPassword(passwordField.getText());
            user.setProvider(false);
            int userId = uDAO.Login(user);

            if (userId > 0){
                user = uDAO.GetUserById(userId);
                UserData.setId(userId);
                UserData.setName(user.getUsername());
                FXMLLoader fxmlLoader;

                if (user.isProvider()){
                    fxmlLoader = new FXMLLoader(getClass().getResource("mycars-view.fxml"));
                }
                else{
                    fxmlLoader = new FXMLLoader(getClass().getResource("főoldal.fxml"));
                }

                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setScene(scene);
            }
            else{
                showAlert("Nincs ilyen felhasználó-jelszó páros");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void navigateToRegisterPage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("regisztracio.fxml"));
            Stage stage = (Stage) registButton.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Helytelen adat");
        alert.setHeaderText(content);
        alert.showAndWait();
    }
}
