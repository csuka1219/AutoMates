package com.automates.automates.repositories;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.UserDAO;

import javax.persistence.*;

public class JpaUserDAO implements UserDAO {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    @Override
    public boolean Register(User newUser) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(newUser);
            entityManager.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public User GetUserById(int id) {
        try {
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean Login(User user) {
        TypedQuery<User> currentUser = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.Username = :username AND u.Password = :password", User.class);
        currentUser.setParameter("username", user.getUsername());
        currentUser.setParameter("password", user.getPassword());
        return !currentUser.getResultList().isEmpty();
    }

    public boolean IsUsernameAlreadyExist(String username){
        TypedQuery<String> usernames = entityManager.createQuery(
                "SELECT a.Username FROM User a where a.Username = :username", String.class);
        usernames.setParameter("username", username);
        return !usernames.getResultList().isEmpty();
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public int getMyClients(int userId) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(DISTINCT l.renter.id) FROM User u JOIN Car c ON u.id = c.provider.id " +
                        "JOIN Loan l ON c.id = l.car.id WHERE  u.id = :userId GROUP BY u.id", Long.class);
        query.setParameter("userId", userId);
        return ((Number) query.getSingleResult()).intValue();
    }
}
