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
}
