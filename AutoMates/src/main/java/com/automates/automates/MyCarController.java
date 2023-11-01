package com.automates.automates;
import com.automates.automates.Model.Car;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MyCarController implements Initializable {

    @FXML
    private TableView<Car> tableView;

    @FXML
    private TableColumn<Car, String> brandColumn;

    @FXML
    private TableColumn<Car, String> licensePlateColumn;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, String> colorColumn;

    @FXML
    private TableColumn<Car, Double> rentalPriceColumn;

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

        configureTableColumns();

        Car car = new Car();
        car.setBrand("asd");
        car.setLicensePlate("asd");
        tableView.getItems().add(car);
        tableView.getItems().add(car);
    }

    private void configureTableColumns(){
        brandColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("Brand"));
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("LicensePlate"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("Model"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("Color"));
        rentalPriceColumn.setCellValueFactory(new PropertyValueFactory<Car,Double>("PricePerDay"));
    }

    private void handleNewCarButton() {

    }

    private void handleEditCarButton() {

    }
}
