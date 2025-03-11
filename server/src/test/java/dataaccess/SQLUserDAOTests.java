package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLUserDAOTests {
    private static final UserData validUser = new UserData("username", "password", "email");
    private static final UserData nullUser = null;
    private static final UserData unregisteredUser = new UserData("unregisteredUsername", "password", "email");

    private static SQLUserDAO userDAO = null;
    private Connection conn = null;

    @BeforeAll
    public static void createDatabase() {
        userDAO = new SQLUserDAO();
        userDAO.clearUsers();
    }

    @BeforeEach
    public void getConnection() {
        conn = DatabaseManager.getConnection();
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        conn.close();
        conn = null;

        userDAO.clearUsers();
    }

    @Test
    public void successAddUser() {
        Assertions.assertDoesNotThrow(() -> {
            userDAO.addUser(validUser);
        });
    }

    @Test
    public void nullUser() {
        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            userDAO.addUser(nullUser);
        });
    }

    @Test
    public void successGetUser() {
        userDAO.addUser(validUser);

        Assertions.assertDoesNotThrow(() -> {
            userDAO.getUser(validUser.username());
        });
    }

    @Test
    public void getUnregisteredUser() {
        userDAO.addUser(validUser);

        Assertions.assertNull(userDAO.getUser(unregisteredUser.username()));
    }

    @Test
    public void successClearUsers() {
        Assertions.assertDoesNotThrow(() -> {
            userDAO.clearUsers();
        });
    }
}
