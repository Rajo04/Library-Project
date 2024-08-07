package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.model.DomainException;
import be.ucll.model.ServiceException;
import be.ucll.model.User;
import be.ucll.service.UserService;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.UserRepository;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void init() {
        this.userService = new UserService(new UserRepository());
    }

    @Test
    public void givenAllUsers_whenAskingAllUsers_thenAllUsersAreReturned() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User("John Doe", 56, "john.doe@ucll.be", "john1234"));
        expectedUsers.add(new User("Jane Toe", 30, "jane.toe@ucll.be", "jane1234")); 
        expectedUsers.add(new User("Jack Doe", 5, "jack.doe@ucll.be", "jack1234"));
        expectedUsers.add(new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234"));
    
        List<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertIterableEquals(expectedUsers, userService.getAllUsers());
    }

    @Test
    public void givenAdultUsers_whenAskingAllAdultUsers_thenAllAdultUsersAreGiven() {
        List<User> foundUsers = userService.getAllAdultUsers();

        int expectedNrOfAdults = 2;
        int expectedMinimumAge = 18;

        assertEquals(expectedNrOfAdults, foundUsers.size());
        for (User user : foundUsers) {
            assertTrue(user.getAge() >= expectedMinimumAge);
        }
    }

    @Test
    public void givenUsersAndAgeRange_whenAskingUsersWithinAgeRange_thenUsersWithinAgeRangeReturned() {
        List<User> foundUsers = userService.getUsersByAgeRange(29, 57);

        assertEquals(2, foundUsers.size());
        assertEquals(foundUsers.get(0).getName(), "John Doe");
        assertEquals(foundUsers.get(1).getName(), "Jane Toe");
    }

    @Test
    public void givenUsersAndAgeRange_whenAskingUsersNotInAgeRange_thenEmptyListReturned() {
        List<User> foundUsers = userService.getUsersByAgeRange(149, 150);

        assertNotNull(foundUsers);
        assertTrue(foundUsers.isEmpty());
    }

    @Test
    public void givenInvalidAgeRange_whenAskingUsersWithAgeRange_thenErrorWithMessageIsThrown() {
        assertThrows(ServiceException.class,
        () -> userService.getUsersByAgeRange(30, 29),
        "Minimum cannot be greater than maximum age.");
    
        assertThrows(ServiceException.class,
        () -> userService.getUsersByAgeRange(1, 151),
        "Age must be between 0 and 150.");

        assertThrows(ServiceException.class,
        () -> userService.getUsersByAgeRange(-1, 150),
        "Age must be between 0 and 150.");
    }

    @Test
    public void givenUsersAndMatchingName_whenFilteringByName_thenUsersGiven() {
        List<User> foundUsers = userService.getUsersByName("Doe");

        assertEquals(3, foundUsers.size());
        assertEquals(foundUsers.get(0).getName(), "John Doe");
        assertEquals(foundUsers.get(1).getName(), "Jack Doe");
        assertEquals(foundUsers.get(2).getName(), "Sarah Doe");
    }

    @Test
    public void givenUsersWithNoMatchingName_whenFilteringByName_thenErrorIsThrown() {
        assertThrows(ServiceException.class,
        () -> userService.getUsersByName("Rajo"),
        "No users with that name found.");
    }

    @Test
    public void givenNonExistingUserWithNonExistingEmail_whenCreatingUser_thenUserIsCreatedAndReturned() {
        User user = new User("Rajo", 20, "rajo@ucll.be", "Rajo123465");
        int userCountBefore = userService.getAllUsers().size();

        User registeredUser = userService.addUser(user);
        int userCountAfter = userService.getAllUsers().size();

        assertEquals(userCountBefore + 1, userCountAfter);
        assertEquals(user, registeredUser);
    }

    @Test
    public void givenNonExistingUserWithExistingEmail_whenCreatingUser_thenExceptionWithMessageThrown() {
        String existingEmail = "john.doe@ucll.be";
        User user = new User("Rajo", 20, existingEmail, "Rajo123546");

        assertThrows(ServiceException.class,
        () -> userService.addUser(user),
        "User already exists.");
    }

    @Test
    public void givenValidUserWithExistingEmail_whenUpdatingUser_thenUserIsUpdatedAndReturned() {
        String existingEmail = "john.doe@ucll.be";
        User user = new User("Rajo", 20, existingEmail, "Rajo123465");
        int userCountBefore = userService.getAllUsers().size();

        User updatedUser = userService.updateUserByEmail(existingEmail, user);
        int userCountAfter = userService.getAllUsers().size();

        assertEquals(userCountBefore, userCountAfter);
        assertEquals(user, updatedUser);
    }

    @Test
    public void givenValidUserWithNonExistingEmail_whenUpdatingUser_thenExceptionWithMessageIsThrown() {
        String nonExistingEmail = "test@test.test";
        User user = new User("Rajo", 20, nonExistingEmail, "Rajo123465");
        
        assertThrows(ServiceException.class,
        () -> userService.updateUserByEmail(nonExistingEmail, user),
        "User does not exist.");
    }

    @Test
    public void givenExistingEmailAndUserWithDifferentEmail_whenUpdatingUser_thenExceptionWithMessageIsThrown() {
        String existingEmail = "john.doe@ucll.be";
        User user = new User("Rajo", 20, "jane.toe@ucll.be", "Rajo123465");
        
        assertThrows(DomainException.class,
        () -> userService.updateUserByEmail(existingEmail, user),
        "Email cannot be changed.");
    }
}
