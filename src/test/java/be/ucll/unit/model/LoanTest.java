package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.model.Book;
import be.ucll.model.DomainException;
import be.ucll.model.Loan;
import be.ucll.model.Magazine;
import be.ucll.model.Publication;
import be.ucll.model.User;

public class LoanTest {
    private LocalDate validStartDate;
    private LocalDate validEndDate;
    private User validUser;
    private List<Publication> validPublications;
    private final int STARTING_NR_OF_COPIES = 10;

    @BeforeEach
    void init(){
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        validStartDate = yesterday;
        validEndDate = today;

        validUser = new User("John Doe", 56, "john.doe@ucll.be", "john1234");

        validPublications = new ArrayList<>();
        validPublications.add(new Book("Vikings", "Arthur", "9783161484100", 2010, STARTING_NR_OF_COPIES));
        validPublications.add(new Magazine("Flair", "Jan", "03785955", 2011, STARTING_NR_OF_COPIES));
    }

    @Test
    public void givenValidValues_whenCreatingLoan_thenLoanIsCreatedAndCopiesDecremented() {
        Loan loan = new Loan(validStartDate, validEndDate, validUser, validPublications);

        assertEquals(validStartDate, loan.getStartDate());
        assertEquals(validEndDate, loan.getEndDate());
        assertEquals(validUser, loan.getUser());
        assertIterableEquals(validPublications, loan.getPublications());    

        for (Publication publication : loan.getPublications()) {
            assertEquals(STARTING_NR_OF_COPIES - 1, publication.getAvailableCopies());
        }
    }

    @Test
    public void givenValidValues_whenReturningLoan_thenCopiesIncrementedAndEndDateSetToToday() {
        Loan loan = new Loan(validStartDate, validEndDate, validUser, validPublications);
        LocalDate today = LocalDate.now();

        loan.returnPublications();

        for (Publication publication : loan.getPublications()) {
            assertEquals(STARTING_NR_OF_COPIES, publication.getAvailableCopies());
        }
        assertEquals(today, loan.getEndDate());
    }

    @Test
    public void givenStartDateNull_whenCreatingLoan_thenErrorWithMessageThrown() {
        LocalDate nullStartDate = null;
        
        assertThrows(DomainException.class,
        () -> new Loan(nullStartDate, validEndDate, validUser, validPublications),
        "Start date is required.");
    }

    @Test
    public void givenFutureStartDate_whenCreatingLoan_thenErrorWithMessageThrown() {
        LocalDate futureStartDate = LocalDate.now().plusDays(1);
        
        assertThrows(DomainException.class,
        () -> new Loan(futureStartDate, validEndDate, validUser, validPublications),
        "Start date cannot be in the future.");
    }

    @Test
    public void givenEndDateBeforeStartDate_whenCreatingLoan_thenErrorWithMessageThrown() {
        LocalDate endDateBeforeStartDate = validStartDate.minusDays(1);
        
        assertThrows(DomainException.class,
        () -> new Loan(validStartDate, endDateBeforeStartDate, validUser, validPublications),
        "Start date cannot be after end date.");
    }

    @Test
    public void givenEndDateInFuture_whenCreatingLoan_thenErrorWithMessageThrown() {
        LocalDate futureEndDate = LocalDate.now().plusDays(1);
        
        assertThrows(DomainException.class,
        () -> new Loan(validStartDate, futureEndDate, validUser, validPublications),
        "End date cannot be in the future.");
    }

    @Test
    public void givenUserNull_whenCreatingLoan_thenErrorWithMessageThrown() {
        User nullUser = null;
        
        assertThrows(DomainException.class,
        () -> new Loan(validStartDate, validEndDate, nullUser, validPublications),
        "User is required.");
    }

    @Test
    public void givenPublicationsNull_whenCreatingLoan_thenErrorWithMessageIsThrown() {
        List<Publication> nullPublications = null;
        
        assertThrows(DomainException.class,
        () -> new Loan(validStartDate, validEndDate, validUser, nullPublications),
        "Publications are required.");
    }

    @Test
    public void givenPublicationsEmpty_whenCreatingLoan_thenErrorWithMessageIsThrown() {
        List<Publication> emptyPublications = new ArrayList<>();
        
        assertThrows(DomainException.class,
        () -> new Loan(validStartDate, validEndDate, validUser, emptyPublications),
        "Publications are required.");
    }

    @Test
    public void givenUnavailablePublications_whenCreatingLoan_thenErrorWithMessageIsThrown() {
        List<Publication> unavailablePublications = new ArrayList<>();
        unavailablePublications.add(new Book("Vikings", "Arthur", "9783161484100", 2010, 0));

        assertThrows(DomainException.class,
        () -> new Loan(validStartDate, validEndDate, validUser, unavailablePublications),
        "Unable to lend publications, no available copies left for Vikings.");
    }
}