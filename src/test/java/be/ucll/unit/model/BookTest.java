package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import be.ucll.model.Book;
import be.ucll.model.DomainException;
import be.ucll.model.Magazine;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class BookTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        validatorFactory.close();
    }

    @Test
    public void givenValidValues_whenCreatingBook_thenBookIsCreated() {
        Book book = new Book("Vikings", "Arthur", "9783161484100", 2010, 10);

        assertEquals("Vikings", book.getTitle());
        assertEquals("Arthur", book.getAuthor());
        assertEquals("9783161484100", book.getIsbn());
        assertEquals(2010, book.getPublicationYear());
    }

    @Test
    public void givenInvalidTitle_whenCreatingBook_thenErrorThrown() {
        String invalidTitle = " ";
        Book book = new Book(invalidTitle, "Arthur", "9783161484100", 2010, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Title is required.", violation.getMessage());
    }

    @Test
    public void givenEmptyTitle_whenCreatingBook_thenErrorThrown() {
        String emptyTitle = null;
        Book book = new Book(emptyTitle, "Arthur", "9783161484100", 2010, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Title is required.", violation.getMessage());
    }

    @Test
    public void givenInvalidAuthor_whenCreatingBook_thenErrorThrown() {
        String invalidAuthor = " ";
        Book book = new Book("Vikings", invalidAuthor, "9783161484100", 2010, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Author is required.", violation.getMessage());
    }

    @Test
    public void givenEmptyAuthor_whenCreatingBook_thenErrorThrown() {
        String emptyAuthor = null;
        Book book = new Book("Vikings", emptyAuthor, "9783161484100", 2010, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Author is required.", violation.getMessage());
    }

    @Test
    public void givenTooShortISBN_whenCreatingBook_thenErrorThrown() {
        String tooShortISBN = "456465544512";
        Book book = new Book("Vikings", "Arthur", tooShortISBN, 2010, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("ISBN is required to have 13 digits and cannot be empty.", violation.getMessage());
    }

    @Test
    public void givenTooLongISBN_whenCreatingBook_thenErrorThrown() {
        String tooLongISBN = "45646554451255";
        Book book = new Book("Vikings", "Arthur", tooLongISBN, 2010, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("ISBN is required to have 13 digits and cannot be empty.", violation.getMessage());
    }

    @Test
    public void givenInvalidISBN_whenCreatingBook_thenErrorThrown() {
        String invalidISBN = " ";
        Book book = new Book("Vikings", "Arthur", invalidISBN, 2010, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("ISBN is required.", violation.getMessage());
    }

    @Test
    public void givenEmptyISBN_whenCreatingBook_thenErrorThrown() {
        String emptyISBN = null;
        Book book = new Book("Vikings", "Arthur", emptyISBN, 2010, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("ISBN is required.", violation.getMessage());
    }

    @Test
    public void givenNegativePublicationYear_whenCreatingBook_thenErrorThrown() {
        int negativePublicationYear = -1;
        Book book = new Book("Vikings", "Arthur", "9783161484100", negativePublicationYear, 10);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Publication year cannot be negative nor in the future.", violation.getMessage());
    }

    @Test
    public void givenFuturePublicationYear_whenCreatingBook_thenErrorThrown() {
        int futurePublicationYear = LocalDate.now().getYear() + 1;
        assertThrows(DomainException.class,
                () -> new Book("Vikings", "Arthur", "9783161484100", futurePublicationYear, 10),
                "Publication year cannot be in the future.");
    }

    @Test
    public void givenNegativeAvailableCopies_whenCreatingBook_thenErrorIsThrown() {
        int negativeAvailableCopies = -1;
        Book book = new Book("Vikings", "Arthur", "9783161484100", 2010, negativeAvailableCopies);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Available copies cannot be negative.", violation.getMessage());
    }
}
