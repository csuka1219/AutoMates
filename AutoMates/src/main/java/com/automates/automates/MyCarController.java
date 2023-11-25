package com.automates.automates;
import com.automates.automates.component.NumberTextField;
import com.automates.automates.Data.UserData;
import com.automates.automates.Model.Car;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.CarDAO;
import com.automates.automates.interfaces.UserDAO;
import com.automates.automates.repositories.JpaCarDAO;
import com.automates.automates.repositories.JpaUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newCarButton.setOnAction(event -> handleNewCarButton());
        editCarButton.setOnAction(event -> handleEditCarButton());

        configureTableColumns();

        //Táblázat feltöltése
        try (CarDAO cDAO = new JpaCarDAO();) {
            tableView.getItems().addAll(cDAO.getCarsByProviderId(UserData.getId()));
        } catch (Exception e) {
            System.out.println("Hiba a táblázat feltöltése közben.");
        }
    }

    private void showAddRecordDialog(boolean editMode, Car editCar) {
        TextField brandField = new TextField();
        TextField modelField = new TextField();
        TextField licensePlateField = new TextField();
        TextField colorField = new TextField();
        NumberTextField seatsField = new NumberTextField();
        NumberTextField priceField = new NumberTextField();
        CheckBox isDieselCheckBox = new CheckBox();
        CheckBox isElectricalOrHybridCheckBox = new CheckBox();

        //ha szerkesztő módban vagyunk akkor beállítjuk a textboxok értékeit
        if(editMode){
            brandField.setText(editCar.getBrand());
            modelField.setText(editCar.getModel());
            licensePlateField.setText(editCar.getLicensePlate());
            colorField.setText(editCar.getColor());
            seatsField.setText(Integer.toString(editCar.getSeats()));
            priceField.setText(Double.toString(editCar.getPricePerDay()));
            isDieselCheckBox.setSelected(editCar.isDiesel());
            isElectricalOrHybridCheckBox.setSelected(editCar.isElectricalOrHybrid());
        }


        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Új autó");
        dialog.setHeaderText("Adja meg az autó adatait");

        // Create the dialog pane
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create content for the dialog
        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Márka:"),
                brandField,
                new Label("Model:"),
                modelField,
                new Label("Rendszám:"),
                licensePlateField,
                new Label("Szín:"),
                colorField,
                new Label("Ülések:"),
                        seatsField,
                new Label("Bérlés ára (ft/nap):"),
                priceField,
                new Label("Diesel:"),
                isDieselCheckBox,
                new Label("Elektromos/Hybrid:"),
                isElectricalOrHybridCheckBox
        );
        dialogPane.setContent(content);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                //kiszedjük a textboxok-ból a szöveget
                String brand = brandField.getText();
                String model = modelField.getText();
                String licensePlate = licensePlateField.getText();
                String color = colorField.getText();
                int seats = Integer.parseInt(seatsField.getText());
                Double price = Double.parseDouble(priceField.getText());
                boolean isDiesel = isDieselCheckBox.isSelected();
                boolean isElectricalOrHybrid = isElectricalOrHybridCheckBox.isSelected();

                //szerkesztő mód
                if(editMode){
                    try (CarDAO cDAO = new JpaCarDAO()) {
                        //Attacholjuk az objektumot, ez fog az adatbázisba kerülni
                        Car existingCar = cDAO.GetCarById(editCar.getId());
                        existingCar.setBrand(brand);
                        existingCar.setModel(model);
                        existingCar.setLicensePlate(licensePlate);
                        existingCar.setColor(color);
                        existingCar.setSeats(seats);
                        existingCar.setPricePerDay(price);
                        existingCar.setDiesel(isDiesel);
                        existingCar.setElectricalOrHybrid(isElectricalOrHybrid);

                        //Ez pedig a táblázatot frissíti
                        editCar.setBrand(brand);
                        editCar.setModel(model);
                        editCar.setLicensePlate(licensePlate);
                        editCar.setColor(color);
                        editCar.setSeats(seats);
                        editCar.setPricePerDay(price);
                        editCar.setDiesel(isDiesel);
                        editCar.setElectricalOrHybrid(isElectricalOrHybrid);
                        tableView.refresh();

                        cDAO.updateCar(existingCar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //Új autó felvétele
                else{
                    User user;
                    try (UserDAO uDAO = new JpaUserDAO()) {
                        user = uDAO.GetUserById(UserData.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    try (CarDAO cDAO = new JpaCarDAO()) {
                        Car newCar = new Car(user, brand, model, licensePlate, color, seats, isDiesel, isElectricalOrHybrid, price);
                        cDAO.saveCar(newCar);
                        tableView.getItems().add(newCar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        });

        // Show the dialog and wait for user input
        dialog.showAndWait();
    }
    private void configureTableColumns(){
        brandColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("Brand"));
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("LicensePlate"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("Model"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("Color"));
        rentalPriceColumn.setCellValueFactory(new PropertyValueFactory<Car,Double>("PricePerDay"));
    }

    private void handleNewCarButton() {
        showAddRecordDialog(false, null);
    }

    private void handleEditCarButton() {
        Car editCar = tableView.getSelectionModel().getSelectedItem();
        if(editCar != null){
            showAddRecordDialog(true, editCar);
        }
    }
}
