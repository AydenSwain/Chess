package service;

import dataAccess.AuthDataAccess;
import dataAccess.MemoryAuthDAO;
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
        Assertions.assertNull(authDAO.getAuthByToken(authToken));
    }

    @Test
    void invalidAuthToken() {
        Assertions.assertThrowsExactly(Unauthorized.class, () -> {
            String incorrectAuthToken = "notRegistered";

            new LogoutService().logout(incorrectAuthToken);
        });
    }
}
