package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import be.ucll.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserTest {
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
    @ValueSource(ints = { 1, 100 })
    public void givenValidValues_whenCreatingUser_thenUserIsCreatedWithThoseValues(int validAge) {
        User user = new User("John Doe", validAge, "john.doe@ucll.be", "john1234");

        assertEquals("John Doe", user.getName());
        assertEquals(validAge, user.getAge());
        assertEquals("john.doe@ucll.be", user.getEmail());
        assertEquals("john1234", user.getPassword());
    }

    @Test
    public void givenBlankName_whenCreatingUser_thenExceptionThrown() {
        String invalidName = " ";
        User user = new User(invalidName, 56, "john.doe@ucll.be", "john1234");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Name is required.", violation.getMessage());
    }

    @Test
    public void givenNullName_whenCreatingUser_thenExceptionThrown() {
        String emptyName = null;
        User user = new User(emptyName, 56, "john.doe@ucll.be", "john1234");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Name is required.", violation.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 101 })
    public void givenInvalidAge_whenCreatingUser_thenExceptionThrown(int invalidAge) {
        User user = new User("John Doe", invalidAge, "john.doe@ucll.be", "john1234");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Age must be a positive integer between 0 and 101.", violation.getMessage());
    }

    @Test
    public void givenTooShortPassword_whenCreatingUser_thenExceptionThrown() {
        String tooShortPassword = "1234567";
        User user = new User("John Doe", 56, "john.doe@ucll.be", tooShortPassword);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("Password must be at least 8 characters long.", violation.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "johndoe.com", "" })
    public void givenInvalidEmail_whenCreatingUser_thenExceptionThrown() {
        String invalidEmail = "johndoe.com";
        User user = new User("John Doe", 56, invalidEmail, "john1234");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("E-mail must be a valid email format", violation.getMessage());
    }
}