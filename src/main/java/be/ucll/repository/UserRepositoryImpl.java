package be.ucll.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.ucll.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> allUsers() {
        return jdbcTemplate.query("SELECT * FROM users",
                new UserRowMapper());
    }

    @Override
    public List<User> usersOlderThan(int age) {
        return jdbcTemplate.query("SELECT * FROM users WHERE age > ?",
                new UserRowMapper(),
                age);
    }

    @Override
    public List<User> findUsersByAgeRange(int min, int max) {
        List<User> usersWithinAgeRange = new ArrayList<>();
        for (User user : users) {
            if (min <= user.getAge() && user.getAge() <= max) {
                usersWithinAgeRange.add(user);
            }
        }
        return usersWithinAgeRange;
    }

    @Override
    public List<User> usersByName(String name) {
        List<User> filteredByName = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredByName.add(user);
            }
        }
        return filteredByName;
    }

    @Override
    public boolean userExists(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User addUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public User updateUserByEmail(String email, User user) {
        User fetchedUser = findUserByEmail(email);
        fetchedUser.setEmail(user.getEmail());
        fetchedUser.setName(user.getName());
        fetchedUser.setAge(user.getAge());
        fetchedUser.setPassword(user.getPassword());
        return fetchedUser;
    }

    @Override
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
