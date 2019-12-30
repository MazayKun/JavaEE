package ru.mikheev.kirill.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс записи в таблице USER
 * @author Kirill Mikheev
 * @version 1.0
 */

public class User {
    /** Глобальный ID пользователей  */
    private static int global_id = 0;

    /** Поля каждой записи */
    private int id;
    private String name;
    private String birthday;
    private String login_ID;
    private String city;
    private String email;
    private String description;

    /**
     * Конструктор принимающий на вход значения всех полей кроме id, который генерируется отдельно
     */
    public User(String name, String birthday, String login_ID, String city, String email, String description) {
        this.id = global_id;
        global_id++;
        this.name = name;
        this.birthday = birthday;
        this.login_ID = login_ID;
        this.city = city;
        this.email = email;
        this.description = description;
    }

    /**
     * Првиатный конструктор, который нужен для создания нового объекта на основе резалт сета
     */
    private User(int id, String name, String birthday, String login_ID, String city, String email, String description) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.login_ID = login_ID;
        this.city = city;
        this.email = email;
        this.description = description;
    }

    /**
     * @return id пользователя типа int
     */
    public int getId() {
        return id;
    }

    /**
     * @return имя пользователя, объект типа String
     */
    public String getName() {
        return name;
    }

    /**
     * @return день родения пользователя, объект типа String
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @return логин пользователя, объект типа String
     */
    public String getLogin_ID() {
        return login_ID;
    }

    /**
     * @return город пользователя, объект типа String
     */
    public String getCity() {
        return city;
    }

    /**
     * @return почту пользователя, объект типа String
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return описание пользователя, объект типа String
     */
    public String getDescription() {
        return description;
    }


    /**
     * Создание пользователя на основе данных из таблицы
     * @param resultSet данные, полученные из таблицы
     * @return новую запись
     * @throws SQLException
     */
    public static User getInstanceBasedOnResultSet(ResultSet resultSet) throws SQLException {
        User newUser = null;
        if(resultSet.next()){
            newUser = new User(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return newUser;
    }
}
