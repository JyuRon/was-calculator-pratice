package org.example.jdbc;

import java.sql.*;

import static org.example.jdbc.ConnectionManager.*;

public class UserDao {
    public void create(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "insert into users values(?,?,?,?)";
        jdbcTemplate.executeUpdate(user, sql, new PreparedStatementSetter() {
            @Override
            public void setter(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        });
    }

    public User findByUserId(String userId) throws SQLException{
        String sql = "select userId, password, name, email, from users where userid = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return (User)jdbcTemplate.executeQuery(userId, sql, new PreparedStatementSetter() {
            @Override
            public void setter(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        }, new RowMapper() {
            @Override
            public Object map(ResultSet resultSet) throws SQLException {
                return new User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email"));
            }
        });
    }

}
