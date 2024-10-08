package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import be.ucll.model.DomainException;
import be.ucll.model.Magazine;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class MagazineTest {
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

    @ParameterizedTest
    @ValueSource(strings = { "03785955", "0378-5955" })
    public void givenValidValues_whenCreatingMagazine_thenMagazineIsCreated(String validIssn) {
        Magazine magazine = new Magazine("Flair", "Jan", validIssn, 2011, 10);

        assertEquals("Flair", magazine.getTitle());
        assertEquals("Jan", magazine.getEditor());
        assertEquals(validIssn, magazine.getIssn());
        assertEquals(2011, magazine.getPublicationYear());
    }

    @Test
    public void givenBlankTitle_whenCreatingMagazine_thenErrorThrown() {
        String invalidTitle = "   ";
        Magazine magazine = new Magazine(invalidTitle, "Jan", "03785955", 2011, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Title is required.", violation.getMessage());
    }

    @Test
    public void givenNullTitle_whenCreatingMagazine_thenErrorThrown() {
        String emptyTitle = null;
        Magazine magazine = new Magazine(emptyTitle, "Jan", "03785955", 2011, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Title is required.", violation.getMessage());
    }

    @Test
    public void givenBlankEditor_whenCreatingMagazine_thenErrorThrown() {
        String invalidEditor = " ";
        Magazine magazine = new Magazine("Flair", invalidEditor, "03785955", 2011, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Editor is required.", violation.getMessage());
    }

    @Test
    public void givenNullEditor_whenCreatingMagazine_thenErrorThrown() {
        String emptyEditor = null;
        Magazine magazine = new Magazine("Flair", emptyEditor, "03785955", 2011, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Editor is required.", violation.getMessage());
    }

    @Test
    public void givenBlankISSN_whenCreatingMagazine_thenErrorThrown() {
        String invalidISSN = " ";
        Magazine magazine = new Magazine("Flair", "Jan", invalidISSN, 2011, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(2, violations.size());
        var violation = violations.iterator().next();
        assertEquals("ISSN has to be exactly 8 characters long and is required.", violation.getMessage());
    }

    @Test
    public void givenNullISSN_whenCreatingMagazine_thenErrorThrown() {
        String emptyISSN = null;
        Magazine magazine = new Magazine("Flair", "Jan", emptyISSN, 2011, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("ISSN has to be exactly 8 characters long and is required.", violation.getMessage());
    }

    @Test
    public void givenTooShortISSN_whenCreatingMagazine_thenErrorThrown() {
        String tooShortISSN = "1234567";
        Magazine magazine = new Magazine("Flair", "Jan", tooShortISSN, 2011, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("ISSN has to be exactly 8 characters long and is required.", violation.getMessage());
    }

    @Test
    public void givenTooLongISSN_whenCreatingMagazine_thenErrorThrown() {
        String tooLongISSN = "123456789";
        Magazine magazine = new Magazine("Flair", "Jan", tooLongISSN, 2011, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("ISSN has to be exactly 8 characters long and is required.", violation.getMessage());
    }

    @Test
    public void givenNegativePublicationYear_whenCreatingMagazine_thenErrorThrown() {
        int negativePublicationYear = -1;
        Magazine magazine = new Magazine("Flair", "Jan", "03785955", negativePublicationYear, 10);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Publication year cannot be negative nor in the future.", violation.getMessage());
    }

    @Test
    public void givenFuturePublicationYear_whenCreatingMagazine_thenErrorThrown() {
        int futurePublicationYear = LocalDate.now().plusYears(1).getYear();
        assertThrows(DomainException.class,
                () -> new Magazine("Flair", "Jan", "03785955", futurePublicationYear, 10),
                "Publication year cannot be in the future.");
    }

    @Test
    public void givenNegativeAvailableCopies_whenCreatingMagazine_thenErrorIsThrown() {
        int negativeAvailableCopies = -1;
        Magazine magazine = new Magazine("Flair", "Jan", "03785955", 2011, negativeAvailableCopies);

        Set<ConstraintViolation<Magazine>> violations = validator.validate(magazine);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Available copies cannot be negative.", violation.getMessage());
    }
}
