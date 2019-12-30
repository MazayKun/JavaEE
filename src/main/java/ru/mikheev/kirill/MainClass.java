package ru.mikheev.kirill;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mikheev.kirill.accessory.RoleDefinitions;
import ru.mikheev.kirill.dao.RoleDAO;
import ru.mikheev.kirill.dao.UserDAO;
import ru.mikheev.kirill.dao.UserRoleDAO;
import ru.mikheev.kirill.entities.Role;
import ru.mikheev.kirill.entities.User;
import ru.mikheev.kirill.entities.UserRole;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Kirill Mikheev
 * @version 1.0
 */

public class MainClass {

    private static final Logger logger = LogManager.getLogger(MainClass.class);

    /** Ставить true если нужно создать и заполнить таблицы, иначе поставить false */
    private final static boolean FILL_KEY = false;

    /** метод создает соединение с бд, и создает 3 таблицы, и заполняет их, если FILL_KEY задан как true */
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "admin");
            if(FILL_KEY) {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                statement.addBatch(
                        "CREATE TABLE USERS( \n" +
                                "    id INTEGER, \n" +
                                "    name VARCHAR(50) NOT NULL, \n" +
                                "    birthday VARCHAR(50) NOT NULL, \n" +
                                "    login_id VARCHAR(100) NOT NULL,\n" +
                                "    city VARCHAR(100) NOT NULL,\n" +
                                "    email VARCHAR(100) NOT NULL,\n" +
                                "    description VARCHAR(100) NOT NULL\n" +
                                ");"
                );
                statement.addBatch(
                        "CREATE TABLE ROLE( \n" +
                                "    id INTEGER, \n" +
                                "    name VARCHAR(50) NOT NULL, \n" +
                                "    description VARCHAR(100) NOT NULL\n" +
                                ");"
                );
                statement.addBatch(
                        "CREATE TABLE USER_ROLE( \n" +
                                "    id INTEGER, \n" +
                                "    user_id INTEGER, \n" +
                                "    role_id INTEGER \n" +
                                ");"
                );
                statement.addBatch(
                        "CREATE TABLE LOGS( \n" +
                                "   id VARCHAR(50) NOT NULL, \n" +
                                "   date VARCHAR(50) NOT NULL, \n" +
                                "   log_level VARCHAR(50) NOT NULL, \n" +
                                "   message VARCHAR(50) NOT NULL, \n" +
                                "   exception VARCHAR(50) NOT NULL\n" +
                                ");"
                );
                statement.executeBatch();
                statement.close();
                connection.commit();
                connection.setAutoCommit(true);
                logger.info("Created 4 main tables");
                fill(connection);
            }
            RoleDAO roleDAO = new RoleDAO();
            UserDAO userDAO = new UserDAO();
            UserRoleDAO userRoleDAO = new UserRoleDAO();
            Role role = roleDAO.getByID(connection, 0);
            User user = userDAO.getByID(connection, 0);
            UserRole userRole = userRoleDAO.getByID(connection, 0);
            System.out.println(role.getName());
            System.out.println(user.getName());
            System.out.println(userRole.getRole_id());


        }catch (ClassNotFoundException e){
            logger.error("Class not found", e);
        }catch (SQLException e){
            logger.error("SQL error with connection", e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("SQl error with close", e);
            }
        }
    }

    /**
     * Метод заполняет базы некоторыми данными
     * @param connection подключение к базе
     */
    private static void fill(Connection connection) throws SQLException {
        User user1 = new User("Ivan", "01.01.2001", "_-_KittyDestroyer_-_", "Moscow", "Ivan01012001@omail.com", "description");
        User user2 = new User("Mark", "02.02.2002", "hi", "Moscow", "Mark02022002@omail.com", "description");
        User user3 = new User("Leo", "03.03.2003", "Leogo", "Moscow", "Leo03032003@omail.com", "description");
        Role role1 = new Role(RoleDefinitions.ADMINISTRATION, "description");
        UserRole userRole1 = new UserRole(10, 10);
        UserRole userRole2 = new UserRole(20, 20);
        UserRole userRole3 = new UserRole(30, 30);
        UserDAO userDAO = new UserDAO();
        userDAO.addEntity(connection, user1);
        userDAO.addEntity(connection, user2);
        userDAO.addEntity(connection, user3);
        logger.info("Filled users table");
        UserRoleDAO userRoleDAO = new UserRoleDAO();
        userRoleDAO.addEntity(connection, userRole1);
        userRoleDAO.addEntity(connection, userRole2);
        userRoleDAO.addEntity(connection, userRole3);
        logger.info("Filled user_role table");
        RoleDAO roleDAO = new RoleDAO();
        roleDAO.addEntity(connection, role1);
        logger.info("Filled role table");
    }
}
