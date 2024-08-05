package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.model.Loan;
import be.ucll.model.ServiceException;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.PublicationRepository;
import be.ucll.repository.UserRepository;
import be.ucll.service.LoanService;

public class LoanServiceTest {
    private LoanService loanService;

    @BeforeEach
    void init() {
        UserRepository userRepository = new UserRepository();
        this.loanService = new LoanService(new LoanRepository(userRepository, new PublicationRepository()),
                userRepository);
    }

    @Test
    void givenUserName_whenGetLoansByUser_thenLoansByUserAreReturned() {
        int expectedLoanCount = 2;
        String username = "john.doe@ucll.be";

        List<Loan> fetchedLoans = this.loanService.getLoansByUser(username, false);

        assertEquals(expectedLoanCount, fetchedLoans.size());
        // Check all loans belong to user with correct username.
        for (Loan loan : fetchedLoans) {
            assertEquals(username, loan.getUser().getEmail());
        }
    }

    @Test
    void givenUsernameAndOnlyActive_whenGetLoansByUser_thenOnlyActiveLoansByUserAreReturned() {
        int expectedLoanCount = 1;
        String username = "john.doe@ucll.be";

        List<Loan> fetchedLoans = this.loanService.getLoansByUser(username, true);

        assertEquals(expectedLoanCount, fetchedLoans.size());
        // Check all loans belong to user with correct username, and that they're active
        // (endDate is empty)
        for (Loan loan : fetchedLoans) {
            assertEquals(username, loan.getUser().getEmail());
            assertNull(loan.getEndDate());
        }
    }

    @Test
    void givenUsernameAndNotActiveLoans_whenGetLoansByUser_thenLoansByUserAreReturned() {
        int expectedLoanCount = 1;
        String username = "jane.toe@ucll.be";

        List<Loan> fetchedLoans = this.loanService.getLoansByUser(username, false);

        assertEquals(expectedLoanCount, fetchedLoans.size());
        // Check all loans belong to user with correct username
        for (Loan loan : fetchedLoans) {
            assertEquals(username, loan.getUser().getEmail());
        }
    }

    @Test
    void givenUserName_whenGetLoansByUserWithNoLoans_thenEmtpyListIsReturned() {
        String username = "jack.doe@ucll.be";

        List<Loan> fetchedLoans = this.loanService.getLoansByUser(username, false);

        assertNotNull(fetchedLoans);
        assertTrue(fetchedLoans.isEmpty());
    }

    @Test
    void givenNonExistingUserName_whenGetLoansByUserWith_thenExcpetionIsThrown() {
        String nonExistingUsername = "foo@bar.com";

        assertThrows(ServiceException.class,
                () -> this.loanService.getLoansByUser(nonExistingUsername, false));
    }
}
