package ru.mikheev.kirill.dao;

import ru.mikheev.kirill.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс для пересылки объектов User между приложением и базой данных
 * @author Kirill Mikheev
 * @version 1.0
 */

public class UserDAO {

    /** Подготовленный шаблон запроса на добавление в базу */
    private final String request = "INSERT INTO postgres.public.\"USER\"(id, name, birthday, login_id, city, email, description) VALUES (?, ?, ?, ?, ?, ?, ?);";

    /**
     * Метод добавляет в таблицу USER новый объект типа User
     * @param connection подключение к нужной базе данных
     * @param entity Объект, который нужно добавить
     * @throws SQLException
     */
    public void addEntity(Connection connection, User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getBirthday());
        statement.setString(4, entity.getLogin_ID());
        statement.setString(5, entity.getCity());
        statement.setString(6, entity.getEmail());
        statement.setString(7, entity.getDescription());
        statement.execute();
        statement.close();
    }
}
