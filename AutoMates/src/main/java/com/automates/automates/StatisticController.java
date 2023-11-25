package com.automates.automates;

import com.automates.automates.Data.UserData;
import com.automates.automates.interfaces.CarDAO;
import com.automates.automates.interfaces.LoanDAO;
import com.automates.automates.interfaces.UserDAO;
import com.automates.automates.repositories.JpaCarDAO;
import com.automates.automates.repositories.JpaLoanDAO;
import com.automates.automates.repositories.JpaUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.chart.XYChart;
import javafx.scene.text.*;
import org.h2.engine.User;

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
        int client = UserData.getId();
        try (CarDAO cDAO = new JpaCarDAO()) {
        myCarsNum.setText("" + cDAO.getNumberOfCarsForUser(client));
        } catch (Exception e) {
            System.out.println("Hiba az adatok kiszámítása közben.");
        }

        try (LoanDAO lDAO = new JpaLoanDAO()) {
            proceeds.setText("" + lDAO.getMyAllProceed(client));
        } catch (Exception e) {
            System.out.println("Hiba az adatok kiszámítása közben.");
        }

        try (UserDAO uDAO = new JpaUserDAO()) {
            clientNum.setText("" + uDAO.getMyClients(client));
        } catch (Exception e) {
            System.out.println("Hiba az adatok kiszámítása közben.");
        }

        //XYChart.Series series1 = new XYChart.Series();
        //series1.setName("Az elmúlt 7 nap");
        //series1.getData().add(new XYChart.Data("1", 25000));
        //barChart.getData().addAll(series1);

    }



}
