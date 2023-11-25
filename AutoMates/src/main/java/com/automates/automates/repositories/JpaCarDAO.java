package com.automates.automates.repositories;

import com.automates.automates.Model.Car;
import com.automates.automates.Model.Loan;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.CarDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaCarDAO implements CarDAO {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    @Override
    public void saveCar(Car car) {
        entityManager.getTransaction().begin();
        entityManager.persist(car);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteCar(Car car) {
        entityManager.getTransaction().begin();
        entityManager.remove(car);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateCar(Car car) {
        saveCar(car);
    }

    @Override
    public List<Car> getCars() {
        TypedQuery<Car> query = entityManager.createQuery(
                "SELECT a FROM Car a", Car.class);
        return query.getResultList();
    }
    @Override
    public List<Car> getAvailableCars() {
        TypedQuery<Car> query = entityManager.createQuery(
                "SELECT a FROM Car a LEFT JOIN Loan o ON a.id = o.car.id WHERE o.EndDate<current_date " +
                        "or a.id not in (select car.id from Loan)", Car.class);
        return query.getResultList();
    }

    @Override
    public Car GetCarById(int id) {
        try {
            return entityManager.find(Car.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }
}
