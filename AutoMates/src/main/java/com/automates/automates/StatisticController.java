package com.automates.automates;

import com.automates.automates.interfaces.CarDAO;
import com.automates.automates.repositories.JpaCarDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
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
