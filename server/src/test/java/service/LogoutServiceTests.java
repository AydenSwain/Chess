package service;

import data_access.AuthDataAccess;
import data_access.MemoryAuthDAO;
import handler.Unauthorized;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogoutServiceTests extends ServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successLogout() {
        AuthData authData = registerValidUser();
        String authToken = authData.authToken();

        new LogoutService().logout(authToken);

        AuthDataAccess authDAO = new MemoryAuthDAO();
        Assertions.assertNull(authDAO.getAuth(authToken));
    }

    @Test
    void invalidAuthToken() {
        Assertions.assertThrowsExactly(Unauthorized.class, () -> {
            String incorrectAuthToken = "notRegistered";

            new LogoutService().logout(incorrectAuthToken);
        });
    }
}
