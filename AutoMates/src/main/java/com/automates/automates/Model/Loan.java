package com.automates.automates.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Loan {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "IdCar")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "IdRenter")
    private User renter;
    private java.util.Date StartDate;
    private java.util.Date EndDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }
}
