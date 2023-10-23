package com.automates.automates.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getLicensePlate() {
        return LicensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        LicensePlate = licensePlate;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getSeats() {
        return Seats;
    }

    public void setSeats(int seats) {
        Seats = seats;
    }

    public boolean isDiesel() {
        return IsDiesel;
    }

    public void setDiesel(boolean diesel) {
        IsDiesel = diesel;
    }

    public double getPricePerDay() {
        return PricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        PricePerDay = pricePerDay;
    }

    @ManyToOne
    @JoinColumn(name = "IdProvider")
    private User provider;
    private String Brand;
    private String Model;
    private String LicensePlate;
    private String Color;
    private int Seats;
    private boolean IsDiesel;
    private double PricePerDay;
}
