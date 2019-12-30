package ru.mikheev.kirill.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mikheev.kirill.entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для пересылки объектов Role между приложением и базой данных
 * @author Kirill Mikheev
 * @version 1.0
 */

public class RoleDAO{
    /** logger */
    private static final Logger logger = LogManager.getLogger(RoleDAO.class);

    /** Подготовленные шаблоны запросов */
    private final String ADD_REQUEST = "INSERT INTO ROLE(id, name, description) VALUES (?, ?, ?);";
    private final String GET_REQUEST = "SELECT * FROM ROLE WHERE id=?;";

    /**
     * Метод добавляет в таблицу ROLE новый объект типа Role
     * @param connection подключение к нужной базе данных
     * @param entity Объект, который нужно добавить
     * @throws SQLException
     */
    public void addEntity(Connection connection, Role entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_REQUEST);
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName().toString());
        statement.setString(3, entity.getDescription());
        statement.execute();
        statement.close();
        logger.info("Add new role with id " + entity.getId());
    }


    /**
     * Получение роли с заданным id
     * @param connection подключение к бд
     * @param id роли, которую нужно получить
     * @return нужная роль
     * @throws SQLException
     */
    public Role getByID(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_REQUEST);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Role role = Role.getInstanceBasedOnResultSet(resultSet);
        resultSet.close();
        statement.close();
        return role;

    }
}
