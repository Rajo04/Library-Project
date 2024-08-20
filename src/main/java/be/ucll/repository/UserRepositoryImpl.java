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
        jdbcTemplate.update("INSERT INTO users (name, age, email, password) VALUES (?, ?, ?, ?)",
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getPassword());
        return user;
    }

    @Override
    public User updateUserByEmail(String email, User user) {
        jdbcTemplate.update("UPDATE users SET name = ?, age = ?, password = ? WHERE email = ?",
                user.getName(),
                user.getAge(),
                user.getPassword(),
                email);
        return findUserByEmail(email);
    }

    @Override
    public void deleteUserByEmail(String email) {
        jdbcTemplate.update("DELETE FROM users WHERE email = ?", email);
    }
}
