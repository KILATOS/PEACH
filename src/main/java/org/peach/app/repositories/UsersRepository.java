package org.peach.app.repositories;

import org.peach.app.models.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/peach";
    private static final String username = "postgres";
    private static final String password = "qwerty007";


    private static Connection connection;
    static {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User findOne(long id){
        try(Statement statement = connection.createStatement()){
            String SQL = String.format("SELECT * FROM users where id = %d", id);
            ResultSet resultSet = statement.executeQuery(SQL);
            User curUser = new User();
            while (resultSet.next()){
                curUser.setId(resultSet.getInt("id"));
                curUser.setName(resultSet.getString("name"));
            }
            return curUser;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return new User();
    }
    public void updateOne(long id, User updatedUser){
        try(PreparedStatement preparedStatement = connection.prepareStatement("update users set id = ? , name = ? where id = ?")){
            preparedStatement.setLong(1,updatedUser.getId());
            preparedStatement.setString(2,updatedUser.getName());
            preparedStatement.setLong(3,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<User> index(){
        List<User> array = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()){
                User curUser = new User();
                curUser.setId(resultSet.getInt("id"));
                curUser.setName(resultSet.getString("name"));
                array.add(curUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }
    public void save(User user){
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into users values (?,?)")){
            preparedStatement.setLong(1,user.getId());
            preparedStatement.setString(2,user.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(long id){
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users where id = ?")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
