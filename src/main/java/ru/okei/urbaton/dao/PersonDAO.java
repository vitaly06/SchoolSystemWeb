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
    public boolean save(Person person, String typeProfile) {
        connect();
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO USERS VALUES('" + person.getName() + "', '" + person.getEmail() +
                    "', '" + person.getPassword() + "', '" + person.getGroupe() + "', '" + typeProfile + "')";
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

    public List<Schedule> getSchedule(String groupe) {
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

    public boolean adminLogin(String email, String password) {
        if (Objects.equals(email, "admin") && Objects.equals(password, "admin")) {
            return true;
        }
        return false;
    }

    public ArrayList<String> getTeachers() {
        connect();
        ArrayList<String> teachers = new ArrayList<String>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM USERS WHERE typeProfile = 'teacher'";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                teachers.add(resultSet.getString("name"));

            }
            connection.close();
            return teachers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<String> getTeachersGroupe() {
        connect();
        ArrayList<String> teachers = new ArrayList<String>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM USERS WHERE typeProfile = 'teacher'";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                teachers.add(resultSet.getString("name") + " " + resultSet.getString("groupe"));

            }
            connection.close();
            return teachers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Schedule> addSchedule(Schedule schedule) {
        List<Schedule> days = new ArrayList<Schedule>();
        String[] times = new String[] {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00-12:00", "12:00-13:00"};
        String[] mondays = schedule.getMonday().split(",");
        String[] tuesday = schedule.getTuesday().split(",");
        String[] wednesday = schedule.getWednesday().split(",");
        String[] thursday = schedule.getThursday().split(",");
        String[] friday = schedule.getFriday().split(",");
        connect();
        try {
            Statement statement = connection.createStatement();
            System.out.println(schedule.getGroupe());
            String SQL = "DELETE FROM GROUPE" + schedule.getGroupe();
            statement.executeUpdate(SQL);
            for(int i = 0; i < 5; i++){
                SQL = "INSERT INTO GROUPE" + schedule.getGroupe() + " VALUES('" + times[i] + "', '" + mondays[i] +
                        "', '" + tuesday[i] + "', '" + wednesday[i] + "', '" + thursday[i]
                        + "', '" + friday[i] + "')";
                statement.executeUpdate(SQL);
            }
            connection.close();
            return days;
            //System.out.println(schedule.getTime() + " " + schedule.getMonday());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }
}