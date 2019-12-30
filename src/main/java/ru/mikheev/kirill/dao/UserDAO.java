package ru.mikheev.kirill.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mikheev.kirill.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс для пересылки объектов User между приложением и базой данных
 * @author Kirill Mikheev
 * @version 1.0
 */

public class UserDAO {

    /** logger */
    private static final Logger logger = LogManager.getLogger(UserDAO.class);

    /** Подготовленные шаблоны запросов */
    private final String ADD_REQUEST = "INSERT INTO USERS(id, name, birthday, login_id, city, email, description) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final String GET_REQUEST = "SELECT * FROM USERS WHERE id=?;";
    private final String GET_REQUEST_BY_DESCRIPTION = "SELECT * FROM USERS WHERE DESCRIPTION=?;";

    /**
     * Метод добавляет в таблицу USER новый объект типа User
     * @param connection подключение к нужной базе данных
     * @param entity Объект, который нужно добавить
     * @throws SQLException
     */
    public void addEntity(Connection connection, User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_REQUEST);
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getBirthday());
        statement.setString(4, entity.getLogin_ID());
        statement.setString(5, entity.getCity());
        statement.setString(6, entity.getEmail());
        statement.setString(7, entity.getDescription());
        statement.execute();
        statement.close();
        logger.info("Add new user with id " + entity.getId());
    }

    /**
     * Получение пользователя с заданным id
     * @param connection подключение к бд
     * @param id пользователя, которого нужно получить
     * @return нужный пользователь
     * @throws SQLException
     */
    public User getByID(Connection connection, int id) throws SQLException {
        logger.info("Запрос на получение пользователя с id = " + id);
        PreparedStatement statement = connection.prepareStatement(GET_REQUEST);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        User user = User.getInstanceBasedOnResultSet(resultSet);
        resultSet.close();
        statement.close();
        return user;
    }

    /**
     * Метод создан исключительно для того, чтоб протестировать его, потому что у всех пользователей в таблице в поле
     * description написано "description", поэтому подразумивается, что при выызове этого метода с таким параметром
     * вернется все содержимой таблицы
     * @param connection соединение с бд
     * @param template шаблон строки, которая должна быть записана в поле description
     * @return ArrayList пользователей, у которых значение поля description совпадает с заданным
     */
    public ArrayList<User> getUserSampleByDescription(Connection connection, String template) throws SQLException {
        logger.info("Запрос на получение всех пользователей, у которых значение поля description совпадает с " + template);
        PreparedStatement statement = connection.prepareStatement(GET_REQUEST_BY_DESCRIPTION);
        statement.setString(1, template);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<User> answer = new ArrayList<>();
        User user = User.getInstanceBasedOnResultSet(resultSet);
        while(user != null){
            answer.add(user);
            user = User.getInstanceBasedOnResultSet(resultSet);
        }
        return answer;
    }
}
