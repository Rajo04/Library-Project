package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import be.ucll.model.User;
import be.ucll.service.UserService;

public class UserServiceTest {
    private UserService userService;

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
}
