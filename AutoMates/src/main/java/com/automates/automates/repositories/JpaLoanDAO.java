package com.automates.automates.repositories;
import com.automates.automates.Model.Loan;
import com.automates.automates.interfaces.LoanDAO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Date;

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
    public List<Loan> getRentedCars(int renterId) {
        TypedQuery<Loan> query = entityManager.createQuery(
                "SELECT l FROM Loan l JOIN Car c ON c.id = l.car.id WHERE l.renter.id = :renterId", Loan.class);
        query.setParameter("renterId", renterId);
        return query.getResultList();
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public int getMyAllProceed(int userId) {
        TypedQuery<Loan> query = entityManager.createQuery(
                "SELECT l FROM Loan l JOIN Car c ON c.id = l.car.id WHERE c.provider.id = :userId", Loan.class);
        query.setParameter("userId", userId);
        List<Loan> l = query.getResultList();
        int myProceed = 0;
        for (Loan loan : l) {
            String date1 = String.valueOf(loan.getStartDate()).substring(0, 22);
            String date2 = String.valueOf(loan.getEndDate()).substring(0, 22);
            String dateP = getCurrentDateTime();

            long differenceInDays;
            try {
                differenceInDays = calculateDateDifference(date1, date2, dateP); //A két időpont közötti napok száma
            } catch (Exception e) {
                return 0;
            }

            try {
                myProceed += (int) ((int)differenceInDays * loan.getCar().getPricePerDay());
            } catch (Exception e) {
                System.out.println("Hiba a bevétel kiszámítása közben.");
            }
        }

        return myProceed;
    }

    @Override
    public int getMyProceedPerDay(int userId, int daysBefore) {
        LocalDateTime dateString1 = LocalDateTime.now().minusDays(daysBefore);
        Date theDay = Date.from(dateString1.atZone(ZoneId.systemDefault()).toInstant());

        Query query = entityManager.createQuery(
                "SELECT SUM(c.PricePerDay) FROM Loan l JOIN Car c ON l.car.id = c.id WHERE c.provider.id = :userId " +
                        "AND :theDay BETWEEN l.StartDate AND l.EndDate",
                   Double.class);

        query.setParameter("userId", userId);
        query.setParameter("theDay",theDay);
        return ((Number) query.getSingleResult()).intValue();
    }

    public static long calculateDateDifference(String dateString1, String dateString2, String dateStringP)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

        LocalDateTime dateTime1 = LocalDateTime.parse(dateString1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(dateString2, formatter);
        LocalDateTime dateTimeP = LocalDateTime.parse(dateStringP, formatter);

        if(dateTimeP.isBefore(dateTime2)){
            dateTime2 = dateTimeP;
        }
        long difference =  java.time.temporal.ChronoUnit.DAYS.between(dateTime1, dateTime2);
        if(difference == 0)
            return 1;
        return difference;
    }
    public static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
        return formatter.format(LocalDateTime.now());
    }
}
