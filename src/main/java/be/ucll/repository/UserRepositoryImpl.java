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
        return jdbcTemplate.query("SELECT * FROM users WHERE ? <= age <= ?",
                new UserRowMapper(),
                min, max);
    }

    @Override
    public List<User> usersByName(String name) {
        return jdbcTemplate.query("SELECT * FROM users WHERE name = ?",
                new UserRowMapper(),
                name);
    }

    @Override
    public boolean userExists(String email) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, email) > 0;
    }

    @Override
    public User findUserByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", new UserRowMapper(), email);
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
