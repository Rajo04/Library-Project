package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import be.ucll.model.User;

public class UserTest {

        @Test
        public void givenValidValues_whenCreatingUser_thenUserIsCreatedWithThoseValues() {
            User user = new User("John Doe", 56, "john.doe@ucll.be", "john1234");
            
            assertEquals("John Doe", user.getName());
            assertEquals(56, user.getAge());
            assertEquals("john.doe@ucll.be", user.getEmail());
            assertEquals("john1234", user.getPassword());
        }
        
        @Test
        public void givenTooShortPassword_whenCreatingUser_thenExceptionThrown(){
            String tooShortPassword = "1234567";
            assertThrows(IllegalArgumentException.class,
            () -> new User("John Doe", 56, "john.doe@ucll.be", tooShortPassword),
            "Password must be at least 8 characters long.");
        }
}
