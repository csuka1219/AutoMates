package com.automates.automates.repositories;
import com.automates.automates.Model.Loan;
import com.automates.automates.interfaces.LoanDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaLoanDAO implements LoanDAO {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    @Override
    public void saveLoan(Loan loan) {
        entityManager.getTransaction().begin();
        entityManager.persist(loan);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteLoan(Loan loan) {
        entityManager.getTransaction().begin();
        entityManager.remove(loan);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateLoan(Loan loan) {
        saveLoan(loan);
    }

    @Override
    public List<Loan> getLoans() {
        TypedQuery<Loan> query = entityManager.createQuery(
                "SELECT a FROM Loan a", Loan.class);
        return query.getResultList();
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }
}
