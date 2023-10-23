package com.automates.automates.repositories;
import com.automates.automates.Model.User;
import com.automates.automates.interfaces.UserDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
}
