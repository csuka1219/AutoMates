package com.automates.automates;

import com.automates.automates.Data.UserData;
import com.automates.automates.Model.Car;
import com.automates.automates.Model.Loan;
import com.automates.automates.interfaces.CarDAO;
import com.automates.automates.interfaces.LoanDAO;
import com.automates.automates.repositories.JpaCarDAO;
import com.automates.automates.repositories.JpaLoanDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.h2.table.Column;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MyLoansController implements Initializable {

    @FXML
    private TableView<Loan> tableView;
    @FXML
    private TableColumn<Loan, String> providerColumn;
    @FXML
    private TableColumn<Loan, String> licensePlateColumn;
    @FXML
    private TableColumn<Loan, Date> startDateColumn;
    @FXML
    private TableColumn<Loan, Date> endDateColumn;


    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();

        int renterId = UserData.getId();
        try (LoanDAO lDAO = new JpaLoanDAO();) {
            tableView.getItems().addAll(lDAO.getRentedCars(renterId));
        } catch (Exception e) {
            System.out.println("Hiba a táblázat feltöltése közben.");
        }

        dateColumnReSize(startDateColumn);
        dateColumnReSize(endDateColumn);

    }
    private void configureTableColumns() {
        //providerColumn.setCellValueFactory(new PropertyValueFactory<Loan, String>("renter"));
        //licensePlateColumn.setCellValueFactory(new PropertyValueFactory<Loan, String>("car"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Loan, Date>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Loan, Date>("endDate"));
        providerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getProvider().getUsername()));
        licensePlateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getLicensePlate()));
    }

    private void dateColumnReSize(TableColumn<Loan, Date> date){
        date.setCellFactory(column -> {
            TableCell<Loan, Date> cell = new TableCell<Loan, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };
            return cell;
        });
    }

}
