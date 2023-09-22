package org.peach.app.repositories;

import org.peach.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    public UsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }





    public User findOne(long id){
        return jdbcTemplate.query("SELECT * FROM users where id = ?",  new UsersMapper(), id).stream().
                findAny().orElse(null);
    }
    public void updateOne(long id, User updatedUser){
        jdbcTemplate.update("update users set id = ?, name = ? where id = ?",updatedUser.getId(),updatedUser.getName(), id );


    }
    public List<User> index(){
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }
    public void save(User user){
        jdbcTemplate.update("insert into users values (?,?)", user.getId(),user.getName());

    }
    public void delete(long id){
        jdbcTemplate.update("delete from users where id = ?", id);

    }
}
