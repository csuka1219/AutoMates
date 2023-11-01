package com.automates.automates;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MyCarController implements Initializable {

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> brandColumn;

    @FXML
    private TableColumn<?, ?> licensePlateColumn;

    @FXML
    private TableColumn<?, ?> modelColumn;

    @FXML
    private TableColumn<?, ?> colorColumn;

    @FXML
    private TableColumn<?, ?> rentalPriceColumn;

    @FXML
    private Button newCarButton;

    @FXML
    private Button editCarButton;

    @FXML
    private AnchorPane sideMenuPane;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Text welcomeText;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newCarButton.setOnAction(event -> handleNewCarButton());
        editCarButton.setOnAction(event -> handleEditCarButton());


    }

    private void handleNewCarButton() {

    }

    private void handleEditCarButton() {

    }
}
