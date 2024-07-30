package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.model.ServiceException;
import be.ucll.model.User;
import be.ucll.service.UserService;
import be.ucll.repository.UserRepository;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void init() {
        this.userService = new UserService(new UserRepository());
    }

    @Test
    public void givenAllUsers_whenAskingAllUsers_thenAllUsersAreReturned() {
        List<User> actualUsers = new ArrayList<>();
        actualUsers.add(new User("John Doe", 56, "john.doe@ucll.be", "john1234"));
        actualUsers.add(new User("Jane Toe", 30, "jane.toe@ucll.be", "jane1234")); 
        actualUsers.add(new User("Jack Doe", 5, "jack.doe@ucll.be", "jack1234"));
        actualUsers.add(new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234"));
    
        assertEquals(actualUsers, userService.getAllUsers());
    }

    @Test
    public void givenAllAdultUsers_whenAskingAllAdultUsers_thenAllAdultUsersAreGiven() {
        List<User> foundUsers = userService.getAllAdults();

        for (User user : foundUsers) {
            assertTrue(user.getAge() >= 18);
        }
    }

    @Test
    public void givenAllUsersWithinAgeRange_whenAskingUsersWithingAgeRange_thenUsersWithinAgeRangeReturned() {
        List<User> foundUsers = userService.getUsersByAgeRange(29, 57);

        assertEquals(2, foundUsers.size());
        assertEquals(foundUsers.get(0).getName(), "John Doe");
        assertEquals(foundUsers.get(1).getName(), "Jane Toe");
    }

    @Test
    public void givenInvalidAgeRange_whenAskingUsersWithAgeRange_thenErrorIsThrown() {
        assertThrows(ServiceException.class,
        () -> {
            List<User> InvalidRange = userService.getUsersByAgeRange(30, 29);
            }
        );
    
        assertThrows(ServiceException.class,
        () -> {
            List<User> InvalidRange = userService.getUsersByAgeRange(1, 151);
            }
        );
    }
}
