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
                new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234")));
    }

    public List<User> allUsers() {
        return users;
    }

    public List<User> usersOlderThan(int age) {
        List<User> allAdultUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getAge() > age) {
                allAdultUsers.add(user);
            }
        }
        return allAdultUsers;
    }

    public List<User> findUsersByAgeRange(int min, int max) {
        List<User> usersWithinAgeRange = new ArrayList<>();
        for (User user : users) {
            if (min <= user.getAge() && user.getAge() <= max) {
                usersWithinAgeRange.add(user);
            }
        }
        return usersWithinAgeRange;
    }

    public List<User> usersByName(String name) {
        List<User> filteredByName = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredByName.add(user);
            }
        }
        return filteredByName;
    }

    public boolean userExists(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public User updateUserByEmail(String email, User user) {
        User fetchedUser = findUserByEmail(email);
        fetchedUser.setEmail(user.getEmail());
        fetchedUser.setName(user.getName());
        fetchedUser.setAge(user.getAge());
        fetchedUser.setPassword(user.getPassword());
        return fetchedUser;
    }

    public void deleteUserByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            if (user.getEmail().equals(email)) {
                users.remove(user);
                break;
            }
        }
    }
}
