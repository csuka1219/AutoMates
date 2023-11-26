package com.automates.automates;

import com.automates.automates.Data.UserData;
import com.automates.automates.Model.Car;
import com.automates.automates.Model.Loan;
import com.automates.automates.Model.User;
import com.automates.automates.component.NumberTextField;
import com.automates.automates.interfaces.CarDAO;
import com.automates.automates.interfaces.LoanDAO;
import com.automates.automates.interfaces.UserDAO;
import com.automates.automates.repositories.JpaCarDAO;
import com.automates.automates.repositories.JpaLoanDAO;
import com.automates.automates.repositories.JpaUserDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class MainPageController implements Initializable {
    @FXML
    private NumberTextField seatsField;
    @FXML
    private ChoiceBox<String> Brand;
    @FXML
    private ChoiceBox<String> Model;
    @FXML
    private ChoiceBox<String> Color;
    @FXML
    private CheckBox isDiesel;
    @FXML
    private CheckBox isElectricalOrHybrid;
    @FXML
    private TableView<Car> tableView;
    @FXML
    private TableColumn<Car, String> ownerColumn;

    @FXML
    private TableColumn<Car, String> licensePlateColumn;

    @FXML
    private TableColumn<Car, String> brandColumn;

    @FXML
    private TableColumn<Car, Double> rentalPriceColumn;
    @FXML
    private TableColumn<Car, String> othersColumn;
    private List<Car> cars = new ArrayList<>();

    @FXML
    private Button searchButton;
    @FXML
    private Button rentButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchButton.setOnAction(event -> handleSearchButton());
        rentButton.setOnAction(event -> handleRentButton());
        configureTableColumns();
        try (CarDAO cDAO = new JpaCarDAO()) {
            cars = cDAO.getAvailableCars();
            tableView.getItems().addAll(cars);
        } catch (Exception e) {
            System.out.println("Hiba a táblázat feltöltése közben");
        }

        Brand.getItems().add("");
        Model.getItems().add("");
        Color.getItems().add("");

        for(Car car : cars){
            if(!Brand.getItems().contains(car.getBrand()))
                Brand.getItems().add(car.getBrand());
            if(!Model.getItems().contains(car.getModel()))
                Model.getItems().add(car.getModel());
            if(!Color.getItems().contains(car.getColor()))
                Color.getItems().add(car.getColor());
        }
    }

    private void configureTableColumns(){
        ownerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProvider().getUsername()));
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("LicensePlate"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("Brand"));
        rentalPriceColumn.setCellValueFactory(new PropertyValueFactory<Car,Double>("PricePerDay"));
        othersColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor().concat(", ")
                .concat(cellData.getValue().getModel())));
    }

    private void handleSearchButton() {
        search();
    }
    private void handleRentButton() {
        rent();
    }

    private void search(){
        List<Car> filteredCars = cars.stream()
                .filter(car ->
                        (Brand.getValue() == null || Brand.getValue().isEmpty() || car.getBrand().equals(Brand.getValue()))
                                && (Model.getValue() == null || Model.getValue().isEmpty() || car.getModel().equals(Model.getValue()))
                                && (Color.getValue() == null || Color.getValue().isEmpty() || car.getColor().equals(Color.getValue()))
                                && car.isDiesel() == isDiesel.isSelected()
                                && car.isElectricalOrHybrid() == isElectricalOrHybrid.isSelected()
                                && (seatsField.getText() == null || seatsField.getText().isEmpty() || car.getSeats() == Integer.parseInt(seatsField.getText())))
                .toList();
        tableView.getItems().clear();
        tableView.refresh();
        tableView.getItems().addAll(filteredCars);
        tableView.refresh();
    }

    private void rent(){
        if(tableView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        NumberTextField daysOfRenting = new NumberTextField();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Új autó");
        dialog.setHeaderText("Adja meg az autó adatait");

        // Create the dialog pane
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create content for the dialog
        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Kölcsönzés hossza (nap):"),
                daysOfRenting
        );
        dialogPane.setContent(content);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                Loan newLoan = new Loan();

                User user;
                try (UserDAO uDAO = new JpaUserDAO()) {
                    user = uDAO.GetUserById(UserData.getId());
                } catch (Exception e) {
                    return null;
                }

                if(daysOfRenting.getText().isEmpty())
                    daysOfRenting.setText("0");

                newLoan.setRenter(user);
                newLoan.setCar(tableView.getSelectionModel().getSelectedItem());
                newLoan.setStartDate(Calendar.getInstance().getTime());
                newLoan.setEndDate(new Date(newLoan.getStartDate().getTime() + (1000L * 60 * 60 * 24 * Integer.parseInt(daysOfRenting.getText()))));

                try (LoanDAO lDAO = new JpaLoanDAO()) {
                    lDAO.saveLoan(newLoan);
                } catch (Exception e) {
                    System.out.println("Hiba a bérlés közben");
                }
                cars.remove(tableView.getSelectionModel().getSelectedItem());
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
                tableView.refresh();
            }
            return null;
        });

        // Show the dialog and wait for user input
        dialog.showAndWait();
    }
}
