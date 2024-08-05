package be.ucll.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import be.ucll.model.Loan;

@Repository
public class LoanRepository {
    public List<Loan> loans;

    public LoanRepository(){
        this.loans = new ArrayList<>();
        // loans.add(new Loan())
    }


    public List<Loan> findLoansByUser(String email, boolean onlyActive){
        List<Loan> filteredLoans = new ArrayList<>();
        for (Loan loan : loans) { // TODO: Implement onlyActive (if onlyActive is true, then only add loan to filtered list if endDate is null --> idea: make an isActive() method in loan)
            if(loan.getUser().getEmail().equals(email)){
                filteredLoans.add(loan);
            }
        }
        return filteredLoans;
    }
}
