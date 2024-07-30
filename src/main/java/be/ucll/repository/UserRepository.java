package be.ucll.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import be.ucll.model.User;

@Repository
public class UserRepository {
    public List<User> users;

    public UserRepository() {
        users = new ArrayList<>(List.of(
        new User("John Doe", 56, "john.doe@ucll.be", "john1234"),
        new User("Jane Toe", 30, "jane.toe@ucll.be", "jane1234"), 
        new User("Jack Doe", 5, "jack.doe@ucll.be", "jack1234"), 
        new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234")
        ));
    }

    public List<User> allUsers() {
        return users;
    }
}
