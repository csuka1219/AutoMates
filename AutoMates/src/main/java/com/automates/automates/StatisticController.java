package com.automates.automates;

import com.automates.automates.Component.NumberTextField;
import com.automates.automates.Data.UserData;
import com.automates.automates.Model.Car;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.CarDAO;
import com.automates.automates.interfaces.UserDAO;
import com.automates.automates.repositories.JpaCarDAO;
import com.automates.automates.repositories.JpaUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.*;

public class StatisticController implements Initializable {

    @FXML
    private Text myCarsNum;
    @FXML
    private Text proceeds;
    @FXML
    private Text clientNum;

    @FXML
    private BarChart barChart;


    @Override
    public void initialize(URL location, ResourceBundle rb) {
        try (CarDAO cDAO = new JpaCarDAO();) {
            int test = cDAO.getNumberOfCarsForUser(1);
        myCarsNum.setText("" + cDAO.getNumberOfCarsForUser(1));
        proceeds.setText("123124");
        clientNum.setText("21");
        } catch (Exception e) {
            System.out.println("Hiba az adatik kiszámítása közben.");
        }


        //  XYChart.Series series1 = new XYChart.Series();
        //  series1.setName("Ma");
        //  series1.getData().add(new XYChart.Data("Hétfő", 23111));
        //   barChart.getData().addAll(series1);

    }



}
