package DAOTestsMockito;

import org.junit.jupiter.api.Test;
import ru.mikheev.kirill.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Класс для тестирования метода на получение сущности юзер на основе резалт сета
 * @author Kirill Mikheev
 * @version 1.0
 */

public class UserTest {

    /** Моковая заглушка резалт сета */
    private ResultSet resultSet = mock(ResultSet.class);

    /** Поля нового юзера */
    private int id = 0;
    private String name = "Ivan";
    private String birthday = "21.12.2012";
    private String login_id = "KittyDestroyer";
    private String city = "Moscow";
    private String mail = "IvanDestroyer@omail.edu";
    private String description = "cluck-cluck";


    @Test
    void getInstanceBasedOnResultSetTest() {
        try {
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(id);
            when(resultSet.getString(2)).thenReturn(name);
            when(resultSet.getString(3)).thenReturn(birthday);
            when(resultSet.getString(4)).thenReturn(login_id);
            when(resultSet.getString(5)).thenReturn(city);
            when(resultSet.getString(6)).thenReturn(mail);
            when(resultSet.getString(7)).thenReturn(description);
            User user = User.getInstanceBasedOnResultSet(resultSet);
            assertEquals(user.getId(), id);
            assertEquals(user.getName(), name);
            assertEquals(user.getBirthday(), birthday);
            assertEquals(user.getLogin_ID(), login_id);
            assertEquals(user.getCity(), city);
            assertEquals(user.getEmail(), mail);
            assertEquals(user.getDescription(), description);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
