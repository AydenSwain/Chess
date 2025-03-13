package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLUserDAOTests {
    private static final UserData VALID_USER = new UserData("username", "password", "email");
    private static final UserData NULL_USER = null;
    private static final UserData UNREGISTERED_USER = new UserData("UNREGISTERED_USERname", "password", "email");

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
            userDAO.addUser(VALID_USER);
        });
    }

    @Test
    public void nullUser() {
        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            userDAO.addUser(NULL_USER);
        });
    }

    @Test
    public void successGetUser() {
        userDAO.addUser(VALID_USER);

        Assertions.assertDoesNotThrow(() -> {
            userDAO.getUser(VALID_USER.username());
        });
    }

    @Test
    public void getUnregisteredUser() {
        userDAO.addUser(VALID_USER);

        Assertions.assertNull(userDAO.getUser(UNREGISTERED_USER.username()));
    }

    @Test
    public void successClearUsers() {
        Assertions.assertDoesNotThrow(() -> {
            userDAO.clearUsers();
        });
    }
}
