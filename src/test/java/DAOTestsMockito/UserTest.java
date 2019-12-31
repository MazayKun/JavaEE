package DAOTestsMockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.mikheev.kirill.dao.UserDAO;
import ru.mikheev.kirill.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Класс тестирует даошку юзера и сам его класс
 * @author Kirill Mikheev
 * @version 1.0
 */

class UserTest {

    /** Даошка, которая будет использована в тестах */
    private UserDAO userDAO;

    /** Моковые заглушки */
    private ResultSet mockResultSet;
    private Connection mockConnection;
    private PreparedStatement mockStatement;

    /** Поля нового юзера */
    private int id = 0;
    private String name = "Ivan";
    private String birthday = "21.12.2012";
    private String login_id = "KittyDestroyer";
    private String city = "Moscow";
    private String mail = "IvanDestroyer@omail.edu";
    private String description = "cluck-cluck";

    /** Строка для тестирования метода на получение выборки пользователей */
    private String getTestDescription = "description";

    /** Шаблоны запроса для тестирования */
    private final String GET_REQUEST = "SELECT * FROM USERS WHERE id=?;";
    private final String GET_REQUEST_BY_DESCRIPTION = "SELECT * FROM USERS WHERE DESCRIPTION=?;";

    /** ограничение на количество вызовов next у резалт сета */
    private int counter;
    /** Считает количество вызовов метода next, которое ограничено переменной counter (ее нужно ресетать перед каждым новым тестом)*/
    private Answer<Boolean> hasNext = new Answer<Boolean>() {
        @Override
        public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
            if(counter <= 0){
                return false;
            }else{
                counter--;
                return true;
            }
        }
    };

    @BeforeEach
    void setUp() throws SQLException {
        mockResultSet = mock(ResultSet.class);

        when(mockResultSet.next()).thenAnswer(hasNext);
        when(mockResultSet.getInt(1)).thenReturn(id);
        when(mockResultSet.getString(2)).thenReturn(name);
        when(mockResultSet.getString(3)).thenReturn(birthday);
        when(mockResultSet.getString(4)).thenReturn(login_id);
        when(mockResultSet.getString(5)).thenReturn(city);
        when(mockResultSet.getString(6)).thenReturn(mail);
        when(mockResultSet.getString(7)).thenReturn(description);

        mockStatement = mock(PreparedStatement.class);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(GET_REQUEST)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(GET_REQUEST_BY_DESCRIPTION)).thenReturn(mockStatement);

        userDAO = new UserDAO();
    }

    /**
     * Тестирует статический метод getInstanceBasedOnResultSet класса User
     * @throws SQLException
     */
    @Test
    void getInstanceBasedOnResultSetTest() throws SQLException {
        counter = 1;
        User user = User.getInstanceBasedOnResultSet(mockResultSet);

        verify(mockResultSet, atMost(2)).next();

        assertEquals(user.getId(), id);
        assertEquals(user.getName(), name);
        assertEquals(user.getBirthday(), birthday);
        assertEquals(user.getLogin_ID(), login_id);
        assertEquals(user.getCity(), city);
        assertEquals(user.getEmail(), mail);
        assertEquals(user.getDescription(), description);
    }

    /**
     * Тестирует метод getByID даошки юзеров
     * @throws SQLException
     */
    @Test
    void getByIDTest() throws SQLException {
        counter = 1;
        User user = userDAO.getByID(mockConnection, 0);

        verify(mockResultSet, atMost(2)).next();

        assertEquals(user.getId(), id);
        assertEquals(user.getName(), name);
        assertEquals(user.getBirthday(), birthday);
        assertEquals(user.getLogin_ID(), login_id);
        assertEquals(user.getCity(), city);
        assertEquals(user.getEmail(), mail);
        assertEquals(user.getDescription(), description);
    }

    /**
     * Тестирует метод getUserSampleByDescription даошки юзеров
     * @throws SQLException
     */
    @Test
    void getUserSampleByDescriptionTest() throws SQLException {
        counter = 3;
        ArrayList<User> users = userDAO.getUserSampleByDescription(mockConnection, getTestDescription);

        verify(mockResultSet, times(4)).next();

        for (User user : users) {
            assertEquals(user.getId(), id);
            assertEquals(user.getName(), name);
            assertEquals(user.getBirthday(), birthday);
            assertEquals(user.getLogin_ID(), login_id);
            assertEquals(user.getCity(), city);
            assertEquals(user.getEmail(), mail);
            assertEquals(user.getDescription(), description);
        }
    }

}
