package dataaccess;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUserDAOTests extends DAOTest {
    private static SQLUserDAO userDAO = null;
    private Connection conn = null;

    @BeforeAll
    public static void createDatabase() {
        userDAO = new SQLUserDAO();
    }

    @BeforeEach
    public void getConnection() throws SQLException {
        conn = DatabaseManager.getConnection();
        conn.setAutoCommit(false);

        String statement = "TRUNCATE TABLE users";
        try (PreparedStatement ps = conn.prepareStatement(statement)){
            ps.executeUpdate();
        }
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        conn.rollback();
        conn.close();
        conn = null;
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

        Assertions.assertThrowsExactly(DataAccessException.class, () -> {
            userDAO.getUser(unregisteredUser.username());
        });
    }

    @Test
    public void successClearUsers() {
        Assertions.assertDoesNotThrow(() -> {
            userDAO.clearUsers();
        });
    }
}
