package com.automates.automates;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.UserDAO;
import com.automates.automates.repositories.JpaUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordAgainField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registButton;

    @FXML
    private TextField usernameField;
    @FXML
    private CheckBox isProviderCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(event -> handleLoginButton());
        registButton.setOnAction(event -> handleRegistButton());
    }

    private void handleLoginButton() {
        navigateToLoginPage();
    }

    private void handleRegistButton() {
        regist();
    }

    private void regist(){
        try (UserDAO uDAO = new JpaUserDAO();) {

            User user = new User();
            user.setUsername(usernameField.getText());
            user.setPassword(passwordField.getText());
            user.setProvider(isProviderCheckBox.isSelected());

            boolean badUserName = uDAO.IsUsernameAlreadyExist(user.getUsername());
            if (badUserName){
                showAlert("Ez a felhasználónév már foglalt, válassz egy másikat");
                return;
            }

            if (passwordField.getText().isEmpty() || passwordAgainField.getText().isEmpty())
                return;

            if (!passwordField.getText().equals(passwordAgainField.getText())){
                showAlert("A jelszavak nem egyeznek");
                return;
            }
            boolean succeed = uDAO.Register(user);

            if (succeed){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Stage stage = (Stage) registButton.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setScene(scene);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Helytelen adat");
        alert.setHeaderText(content);
        alert.showAndWait();
    }
    private void navigateToLoginPage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) registButton.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
