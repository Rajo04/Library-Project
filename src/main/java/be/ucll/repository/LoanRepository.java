package be.ucll.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import be.ucll.model.Loan;
import be.ucll.model.Publication;
import be.ucll.model.User;

@Repository
public class LoanRepository {
    private UserRepository userRepository;
    private PublicationRepository publicationRepository;

    public List<Loan> loans;

    public LoanRepository(UserRepository userRepository, PublicationRepository publicationRepository) {
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
        initLoans();
    }

    private void initLoans() {
        User user1 = this.userRepository.users.get(0);
        User user2 = this.userRepository.users.get(1);
        Publication pub1 = this.publicationRepository.books.get(0);
        Publication pub2 = this.publicationRepository.magazines.get(0);
        LocalDate today = LocalDate.now();

        this.loans = new ArrayList<>();
        // Inactive + active loans for user1
        this.loans.add(new Loan(today.minusDays(2), today.minusDays(1), user1, List.of(pub1)));
        this.loans.add(new Loan(today, null, user1, List.of(pub2)));

        // Inactive loan for user2
        this.loans.add(new Loan(today.minusDays(2), today.minusDays(1), user2, List.of(pub1)));
    }

    public List<Loan> findLoansByUser(String email, boolean onlyActive) {
        List<Loan> filteredLoans = new ArrayList<>();
        for (Loan loan : loans) { // TODO: Implement onlyActive (if onlyActive is true, then only add loan to
                                  // filtered list if endDate is null --> idea: make an isActive() method in loan)
            if (loan.getUser().getEmail().equals(email)) {
                if (onlyActive && !loan.isActive()) {
                    continue;
                }
                filteredLoans.add(loan);
            }
        }
        return filteredLoans;
    }

    public void deleteLoansForUserByEmail(String email) {
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            if (loan.getUser().getEmail().equals(email)) {
                loans.remove(loan);
           }
        }
    }
}
