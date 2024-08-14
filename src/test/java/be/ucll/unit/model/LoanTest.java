package be.ucll.unit.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.model.Book;
import be.ucll.model.DomainException;
import be.ucll.model.Loan;
import be.ucll.model.Magazine;
import be.ucll.model.Publication;
import be.ucll.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {
    private LocalDate validStartDate;
    private LocalDate validEndDate;
    private User validUser;
    private List<Publication> validPublications;
    private final int STARTING_NR_OF_COPIES = 10;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeEach
    void init() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        validStartDate = yesterday;
        validEndDate = today;

        validUser = new User("John Doe", 56, "john.doe@ucll.be", "john1234");

        validPublications = new ArrayList<>();
        validPublications.add(new Book("Vikings", "Arthur", "9783161484100", 2010, STARTING_NR_OF_COPIES));
        validPublications.add(new Magazine("Flair", "Jan", "03785955", 2011, STARTING_NR_OF_COPIES));
    }

    @BeforeAll
    static void init1() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void teardown() {
        validatorFactory.close();
    }

    @Test
    public void givenValidValues_whenCreatingLoan_thenLoanIsCreatedAndCopiesDecremented() {
        Loan loan = new Loan(validStartDate, null, validUser, validPublications);

        assertEquals(validStartDate, loan.getStartDate());
        assertNull(loan.getEndDate());
        assertEquals(validUser, loan.getUser());
        assertIterableEquals(validPublications, loan.getPublications());

        for (Publication publication : loan.getPublications()) {
            assertEquals(STARTING_NR_OF_COPIES - 1, publication.getAvailableCopies());
        }
    }

    @Test
    public void givenValidValues_whenReturningLoan_thenCopiesIncrementedAndEndDateSetToToday() {
        Loan loan = new Loan(validStartDate, null, validUser, validPublications);
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
        Loan loan = new Loan(nullStartDate, validEndDate, validUser, validPublications);

        Set<ConstraintViolation<Loan>> violations = validator.validate(loan);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Start date is required.", violation.getMessage());
    }

    @Test
    public void givenFutureStartDate_whenCreatingLoan_thenErrorWithMessageThrown() {
        LocalDate futureStartDate = LocalDate.now().plusDays(1);
        Loan loan = new Loan(futureStartDate, null, validUser, validPublications);

        Set<ConstraintViolation<Loan>> violations = validator.validate(loan);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Start date cannot be in the future.", violation.getMessage());
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
        Loan loan = new Loan(validStartDate, validEndDate, nullUser, validPublications);

        Set<ConstraintViolation<Loan>> violations = validator.validate(loan);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("User is required.", violation.getMessage());
    }

    @Test
    public void givenPublicationsNull_whenCreatingLoan_thenErrorWithMessageIsThrown() {
        List<Publication> nullPublications = null;
        Loan loan = new Loan(validStartDate, validEndDate, validUser, nullPublications);

        Set<ConstraintViolation<Loan>> violations = validator.validate(loan);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Publications are required.", violation.getMessage());
    }

    @Test
    public void givenPublicationsEmpty_whenCreatingLoan_thenErrorWithMessageIsThrown() {
        List<Publication> emptyPublications = new ArrayList<>();
        Loan loan = new Loan(validStartDate, validEndDate, validUser, emptyPublications);

        Set<ConstraintViolation<Loan>> violations = validator.validate(loan);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Publications are required.", violation.getMessage());
    }

    @Test
    public void givenUnavailablePublications_whenCreatingLoan_thenErrorWithMessageIsThrown() {
        List<Publication> unavailablePublications = new ArrayList<>();
        unavailablePublications.add(new Book("Vikings", "Arthur", "9783161484100", 2010, 0));

        assertThrows(DomainException.class,
                () -> new Loan(validStartDate, null, validUser, unavailablePublications),
                "Unable to lend publications, no available copies left for Vikings.");
    }
}
