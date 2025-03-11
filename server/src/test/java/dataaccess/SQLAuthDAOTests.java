package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLAuthDAOTests {
    private static final AuthData validAuth = new AuthData("username", "authToken");
    private static final AuthData nullAuth = null;
    private static final AuthData unauthorizedAuth = new AuthData("username", "unauthorizedToken");

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
            authDAO.addAuth(validAuth);
        });
    }

    @Test
    public void nullAuth() {
        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            authDAO.addAuth(nullAuth);
        });
    }

    @Test
    public void successGetAuth() {
        authDAO.addAuth(validAuth);

        Assertions.assertDoesNotThrow(() -> {
            authDAO.getAuth(validAuth.authToken());
        });
    }

    @Test
    public void getUnauthorizedAuth() {
        authDAO.addAuth(validAuth);

        Assertions.assertNull(authDAO.getAuth(unauthorizedAuth.authToken()));
    }

    @Test
    public void successClearAuths() {
        Assertions.assertDoesNotThrow(() -> {
            authDAO.clearAuths();
        });
    }
}
