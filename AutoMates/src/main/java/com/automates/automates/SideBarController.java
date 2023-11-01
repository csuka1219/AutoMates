package com.automates.automates;

import com.automates.automates.Data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {
    @FXML
    private Text usernameText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameText.setText(UserData.getName());
    }
}
