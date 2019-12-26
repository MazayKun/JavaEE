package ru.mikheev.kirill.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mikheev.kirill.entities.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс для пересылки объектов UserRole между приложением и базой данных
 * @author Kirill Mikheev
 * @version 1.0
 */

public class UserRoleDAO {

    /** logger */
    private static final Logger logger = LogManager.getLogger(UserRoleDAO.class);

    /** Подготовленный шаблон запроса на добавление в базу */
    private final String request = "INSERT INTO USER_ROLE(id, user_id, role_id) VALUES (?, ?, ?);";

    /**
     * Метод добавляет в таблицу USER_ROLE новый объект типа UserRole
     * @param connection подключение к нужной базе данных
     * @param entity Объект, который нужно добавить
     * @throws SQLException
     */
    public void addEntity(Connection connection, UserRole entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setInt(1, entity.getId());
        statement.setInt(2, entity.getUser_id());
        statement.setInt(3, entity.getRole_id());
        statement.execute();
        statement.close();
        logger.info("Add new user_role with id " + entity.getId());
    }
}
