package com.automates.automates.repositories;

import com.automates.automates.Model.Car;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.CarDAO;

import javax.persistence.*;
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
    public Car GetCarById(int id) {
        try {
            return entityManager.find(Car.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getNumberOfCarsForUser(int userId) {
        try {
            Query query = entityManager.createQuery(
                    "SELECT COUNT(c) FROM Car c WHERE c.provider.id = :userId", Long.class);
            query.setParameter("userId", userId);
            return ((Number) query.getSingleResult()).intValue();

        } catch (Exception e) {
            return 0;
        }
    }


    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }
}
