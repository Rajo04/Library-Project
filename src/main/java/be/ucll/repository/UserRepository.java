package be.ucll.repository;

import be.ucll.model.User;

import java.util.List;

public interface UserRepository {
    List<User> allUsers();

    List<User> usersOlderThan(int age);

    List<User> findUsersByAgeRange(int min, int max);

    List<User> usersByName(String name);

    boolean userExists(String email);

    User findUserByEmail(String email);

    User addUser(User user);

    User updateUserByEmail(String email, User user);

    void deleteUserByEmail(String email);
}
