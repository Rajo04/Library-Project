package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import be.ucll.model.DomainException;
import be.ucll.model.User;

public class UserTest {

        @ParameterizedTest
        @ValueSource(ints = {1, 100})
        public void givenValidValues_whenCreatingUser_thenUserIsCreatedWithThoseValues(int validAge) {
            User user = new User("John Doe", validAge, "john.doe@ucll.be", "john1234");
            
            assertEquals("John Doe", user.getName());
            assertEquals(validAge, user.getAge());
            assertEquals("john.doe@ucll.be", user.getEmail());
            assertEquals("john1234", user.getPassword());
        }
        
        @Test
        public void givenInvalidName_whenCreatingUser_thenExceptionThrown(){
            String invalidName = " ";
            assertThrows(DomainException.class,
            () -> new User(invalidName, 56, "john.doe@ucll.be", "john1234"),
            "Name is required.");
        }

        @Test
        public void givenEmptyName_whenCreatingUser_thenExceptionThrown(){
            String emptyName = null;
            assertThrows(DomainException.class,
            () -> new User(emptyName, 56, "john.doe@ucll.be", "john1234"),
            "Name is required.");
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 0, 101})
        public void givenInvalidAge_whenCreatingUser_thenExceptionThrown(int invalidAge){
            assertThrows(DomainException.class,
            () -> new User("John Doe", invalidAge, "john.doe@ucll.be", "john1234"),
            "Age must be a positive integer between 0 and 101.");
        }

        @Test
        public void givenTooShortPassword_whenCreatingUser_thenExceptionThrown(){
            String tooShortPassword = "1234567";
            assertThrows(DomainException.class,
            () -> new User("John Doe", 56, "john.doe@ucll.be", tooShortPassword),
            "Password must be at least 8 characters long.");
        }

        @ParameterizedTest
        @ValueSource(strings = {"johndoe.com", ""})
        public void givenInvalidEmail_whenCreatingUser_thenExceptionThrown(){
            String invalidEmail = "johndoe.com";
            assertThrows(DomainException.class,
            () -> new User("John Doe", 56, invalidEmail, "john1324"),
            "E-mail must be a valid email format.");
        }
}