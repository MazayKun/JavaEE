package DAOTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.mikheev.kirill.accessory.RoleDefinitions;
import ru.mikheev.kirill.dao.RoleDAO;
import ru.mikheev.kirill.entities.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *  Класс для тестирования get запроса RoleDAO (первая часть задания, для тестирования add запроса нужно прибегнуть к мокито)
 *  Не использовал BeforeAll потому что не хочу возиться со статиками
 * @author Kirill Mikheev
 * @version 1.0
 */

class RoleDAOTest {

    /** Необходимые для тестирования объекты */
    private Connection connection = null;
    private RoleDAO roleDAO;

    /** Ключ на проверку того, что соединение успешно создано*/
    private boolean isConnected = false;

    /**
     * Задает все начальные значения
     */
    @BeforeEach
    void setUpTest() {
        roleDAO = new RoleDAO();
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
            Role role = roleDAO.getByID(connection, 0);
            assertEquals(role.getName().toString(), RoleDefinitions.ADMINISTRATION.toString());
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
