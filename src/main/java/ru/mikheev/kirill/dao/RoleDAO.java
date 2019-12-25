package ru.mikheev.kirill.dao;

import ru.mikheev.kirill.entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс для пересылки объектов Role между приложением и базой данных
 * @author Kirill Mikheev
 * @version 1.0
 */

public class RoleDAO{

    /** Подготовленный шаблон запроса на добавление в базу */
    private final String request = "INSERT INTO postgres.public.\"ROLE\"(id, name, description) VALUES (?, ?, ?);";

    /**
     * Метод добавляет в таблицу ROLE новый объект типа Role
     * @param connection подключение к нужной базе данных
     * @param entity Объект, который нужно добавить
     * @throws SQLException
     */
    public void addEntity(Connection connection, Role entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName().toString());
        statement.setString(3, entity.getDescription());
        statement.execute();
        statement.close();
    }
}
