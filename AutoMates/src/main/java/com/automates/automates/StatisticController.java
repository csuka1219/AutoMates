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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.chart.XYChart;
import javafx.scene.text.*;


public class StatisticController implements Initializable {

    @FXML
    private Text myCarsNum;
    @FXML
    private Text proceeds;
    @FXML
    private Text clientNum;

    @FXML
    public BarChart barChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int userId = UserData.getId();
        try (CarDAO cDAO = new JpaCarDAO()) {
            myCarsNum.setText("" + cDAO.getNumberOfCarsForUser(userId));
        } catch (Exception e) {
            System.out.println("Hiba az adatok kiszámítása közben.");
        }


        try (LoanDAO lDAO = new JpaLoanDAO()) {
            proceeds.setText("" + lDAO.getMyAllProceed(userId));
        } catch (Exception e) {
            System.out.println("Hiba az adatok kiszámítása közben.");
        }


        try (UserDAO uDAO = new JpaUserDAO()) {
            clientNum.setText("" + uDAO.getMyClients(userId));

        } catch (Exception e) {
            System.out.println("Hiba az adatok kiszámítása közben.");
        }


        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Az elmúlt 7 nap (Hónap/nap)");
        for (int i = 7; i > 0; i--) {
            int result = uploadGraph(userId, i);
            String date = String.valueOf(LocalDate.now().minusDays(i));
            date = date.substring(5);
            series1.getData().add(new XYChart.Data(date, result));
        }
        barChart.getData().addAll(series1);

    }


    private int uploadGraph(int userId, int i) {
        int result = 0;
        try (LoanDAO lDAO = new JpaLoanDAO()) {
            result = lDAO.getMyProceedPerDay(userId, i);
        } catch (Exception e) {
            System.out.println("Hiba az adatok kiszámítása közben.");
        }
        return result;
    }
}
