package ru.okei.urbaton.dao;

import org.springframework.stereotype.Component;
import ru.okei.urbaton.models.Person;

import java.sql.*;
import java.util.Objects;

@Component
public class PersonDAO {
    public PersonDAO() {
    }

    private static Connection connection;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\123\\Desktop\\java\\SchoolSystemWeb\\src\\main\\webapp\\resources\\ssw_db.s3db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(Person person) {
        connect();
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO USERS VALUES('" + person.getName() + "', '" + person.getEmail() +
                    "', '" + person.getPassword() + "', '" + person.getNaprav() + "', '" + person.getTypeProfile() + "')";
            statement.executeUpdate(SQL);
            connection.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean login(Person person){
        connect();
        try{
            Statement statement = connection.createStatement();
            String SQL = "SELECT password FROM USERS WHERE email = '" + person.getEmail() + "'";
            ResultSet resultSet = statement.executeQuery(SQL);
            String password = resultSet.getString("password");
            if (Objects.equals(person.getPassword(), password)) {
                System.out.println("hi");
                connection.close();
                return true;
            }
            System.out.println("bye");
            connection.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String[] getData(Person person){
        connect();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM USERS WHERE email = '" + person.getEmail() + "'";
            ResultSet resultSet = statement.executeQuery(SQL);
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String naprav = resultSet.getString("naprav");
            String typeProfile = resultSet.getString("typeProfile");
            String[] data =  new String[] {name, email, password, naprav, typeProfile};
            connection.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new String[5];
    }
}
