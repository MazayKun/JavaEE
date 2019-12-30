package DAOTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mikheev.kirill.dao.UserDAO;
import ru.mikheev.kirill.entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 *  Класс для тестирования get запроса UserDAO (первая часть задания, для тестирования add запроса нужно прибегнуть к мокито)
 *  Не использовал BeforeAll потому что не хочу возиться со статиками
 * @author Kirill Mikheev
 * @version 1.0
 */

class UserDAOTest {

    /** Необходимые для тестирования объекты */
    private Connection connection = null;
    private UserDAO userDAO;

    /** Ключ на проверку того, что соединение успешно создано*/
    private boolean isConnected = false;


    /**
     * Задает все начальные значения
     */
    @BeforeEach
    void setUpTest() {
        userDAO = new UserDAO();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "admin");
            isConnected = true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Тестирует полученный с помощью даошки объект на соответствие правильному
     */
    @Test
    void testGetRequest() {
        assumeTrue(isConnected);
        try {
            User user = userDAO.getByID(connection, 0);
            assertEquals(user.getName(), "Ivan");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error");
        }
    }

    /**
     * Закрывает соединение после выполнения теста
     */
    @AfterEach
    void closeTest() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
