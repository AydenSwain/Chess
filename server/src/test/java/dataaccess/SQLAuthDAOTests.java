package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLAuthDAOTests {
    private static final AuthData VALID_AUTH = new AuthData("username", "authToken");
    private static final AuthData NULL_AUTH = null;
    private static final AuthData UNAUTHORIZED_AUTH = new AuthData("username", "unauthorizedToken");

    private static SQLAuthDAO authDAO = null;
    private Connection conn = null;

    @BeforeAll
    public static void createDatabase() {
        authDAO = new SQLAuthDAO();
        authDAO.clearAuths();
    }

    @BeforeEach
    public void getConnection() throws SQLException {
        conn = DatabaseManager.getConnection();
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        conn.close();
        conn = null;

        authDAO.clearAuths();
    }

    @Test
    public void successAddAuth() {
        Assertions.assertDoesNotThrow(() -> {
            authDAO.addAuth(VALID_AUTH);
        });
    }

    @Test
    public void nullAuth() {
        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            authDAO.addAuth(NULL_AUTH);
        });
    }

    @Test
    public void successGetAuth() {
        authDAO.addAuth(VALID_AUTH);

        Assertions.assertDoesNotThrow(() -> {
            authDAO.getAuth(VALID_AUTH.authToken());
        });
    }

    @Test
    public void getUnauthorizedAuth() {
        authDAO.addAuth(VALID_AUTH);

        Assertions.assertNull(authDAO.getAuth(UNAUTHORIZED_AUTH.authToken()));
    }

    @Test
    public void successClearAuths() {
        Assertions.assertDoesNotThrow(() -> {
            authDAO.clearAuths();
        });
    }
}
