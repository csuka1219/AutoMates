package com.automates.automates.interfaces;

import com.automates.automates.Model.Loan;

import java.util.List;

public interface LoanDAO extends AutoCloseable{
    public void saveLoan(Loan loan); //C
    public void deleteLoan(Loan loan); //D
    public void updateLoan(Loan loan); //U
    public List<Loan> getLoans(); //R
    public List<Loan> getRentedCars(int renterId);
    public int getMyAllProceed(int userId);

}
