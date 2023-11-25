package com.automates.automates.interfaces;

import com.automates.automates.Model.Car;

import java.util.List;

public interface CarDAO extends AutoCloseable{
    public void saveCar(Car car); //C
    public void deleteCar(Car car); //D
    public void updateCar(Car car); //U
    public List<Car> getCars(); //R
    public List<Car> getCarsByProviderId(int providerId); //R
    public List<Car> getAvailableCars();
    public Car GetCarById(int id);
    public int getNumberOfCarsForUser(int userId);


}