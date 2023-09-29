package org.peach.app.repositories;

import org.peach.app.models.User;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    public UsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }





    public User findOneById(long id){
        return jdbcTemplate.query("SELECT * FROM users where id = ?",  new UsersMapper(), id).stream().
                findAny().orElse(null);
    }
    public Optional<User> findOneByName(String name){
        return jdbcTemplate.query("SELECT * FROM users WHERE name = ?",new BeanPropertyRowMapper<>(User.class), name).
                stream().findAny();
    }
    public void updateOne(long id, User updatedUser){
        jdbcTemplate.update("update users set name = ?, address = ? where id = ?",updatedUser.getName(),updatedUser.getAddress(), id );

    }
    public void updateIsAdmin(User userToUpdate){
        jdbcTemplate.update("update  users set isadmin = ? where id = ?", true, userToUpdate.getId());
    }
    public List<User> index(){
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }
    public void save(User user){
        jdbcTemplate.update("insert into users (name, address) values (?, ?)", user.getName(), user.getAddress());

    }
    public void delete(long id){
        jdbcTemplate.update("delete from users where id = ?", id);

    }

    ///////////////////////////////////////////////
    /////////// Testing batch insertion ///////////
    ///////////////////////////////////////////////

    public void insertMultiple(){
        List<User> usersToAdd = createManyUsers();
        long before = System.currentTimeMillis();
        for(User curUser : usersToAdd){
            jdbcTemplate.update("insert into users values (?,?)", curUser.getId(),curUser.getName());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time of multiple insertion " + (after - before));

    }
    private static List<User> createManyUsers(){
        List<User> result = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User curUser = new User(i,"Name" + i, "dummyEmail" + i + "@gmail.com");
            result.add(curUser);
        }
        return result;
    }

    public void insertBatch() {
        List<User> usersToAdd = createManyUsers();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("insert into users values (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1,usersToAdd.get(i).getId());
                preparedStatement.setString(2,usersToAdd.get(i).getName());
            }

            @Override
            public int getBatchSize() {
                return usersToAdd.size();
            }
        });

        long after = System.currentTimeMillis();
        System.out.println("Time of batch insertion " + (after - before));


    }
    public void deleteBatch(){
        jdbcTemplate.batchUpdate("delete from users where id = ?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1,i);
            }

            @Override
            public int getBatchSize() {
                return 1000;
            }
        });
    }
}
