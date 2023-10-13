package org.peach.app.dao;

import org.peach.app.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User curUser = new User();

        curUser.setId(resultSet.getInt("id"));
        curUser.setName(resultSet.getString("name"));
        return curUser;
    }
}
