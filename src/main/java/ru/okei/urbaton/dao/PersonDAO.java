package ru.okei.urbaton.dao;

import org.springframework.stereotype.Component;
import ru.okei.urbaton.models.Person;
import ru.okei.urbaton.models.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class PersonDAO {
    public PersonDAO() {
    }

    private static Connection connection;
    // Связь с БД
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

    // Регистрация
    public boolean save(Person person) {
        connect();
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO USERS VALUES('" + person.getName() + "', '" + person.getEmail() +
                    "', '" + person.getPassword() + "', '" + person.getGroupe() + "', '" + person.getTypeProfile() + "')";
            statement.executeUpdate(SQL);
            connection.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // Логин
    public boolean login(Person person) {
        connect();
        try {
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
    public List<Schedule> getSchedule(String groupe){
        List<Schedule> days = new ArrayList<Schedule>();
        connect();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM GROUPE" + groupe;
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("time"));
                days.add(new Schedule(resultSet.getString("time"),
                        resultSet.getString("monday"),
                        resultSet.getString("tuesday"),
                        resultSet.getString("wednesday"),
                        resultSet.getString("thursday"),
                        resultSet.getString("friday")));
            }
            connection.close();
            return days;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    // Данные о пользователе
    public String[] getData(Person person) {
        connect();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM USERS WHERE email = '" + person.getEmail() + "'";
            ResultSet resultSet = statement.executeQuery(SQL);
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String groupe = resultSet.getString("groupe");
            String typeProfile = resultSet.getString("typeProfile");
            String[] data = new String[]{name, email, password, groupe, typeProfile};
            connection.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new String[5];
    }
    public boolean adminLogin(String email, String password){
        if (Objects.equals(email, "admin") && Objects.equals(password, "admin")){
            return true;
        }
        return false;
    }
}
