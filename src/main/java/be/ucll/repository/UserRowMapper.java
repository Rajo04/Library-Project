package be.ucll.repository;

import be.ucll.model.User;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("NAME"), rs.getInt("AGE"), rs.getString("EMAIL"), rs.getString("PASSWORD"));
    }
}
