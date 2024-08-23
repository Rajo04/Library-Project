package be.ucll.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import be.ucll.model.Book;
import be.ucll.model.Loan;
import be.ucll.model.Magazine;
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
        List<User> users = new ArrayList<>(List.of(
                new User("John Doe", 56, "john.doe@ucll.be", "john1234"),
                new User("Jane Toe", 30, "jane.toe@ucll.be", "jane1234"),
                new User("Jack Doe", 5, "jack.doe@ucll.be", "jack1234"),
                new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234")));
        User user1 = users.get(0);
        User user2 = users.get(1);
        List<Publication> books = new ArrayList<>(List.of(
                new Book("Vikings", "Arthur", "9783161484100", 2010, 10),
                new Book("Annabel", "James Barnes", "9783161484140", 2014, 10),
                new Book("The Hunger Games", "Suzanne collins", "9783161488140", 2011, 10),
                new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10)));

        List<Publication> magazines = new ArrayList<>(List.of(
                new Magazine("The Guardian", "James Barnes", "12345678", 2010, 10),
                new Magazine("The New York Times", "Suzanne Collins", "12345671", 2014, 10),
                new Magazine("The Washington Post", "Josh Jefferson", "12345672", 2011, 10),
                new Magazine("The Wall Street Journal", "Josh Jefferson", "12345673", 2008, 10)));
        Publication pub1 = books.get(0);
        Publication pub2 = magazines.get(0);
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
