package service;

import dataAccess.AuthDataAccess;
import dataAccess.MemoryAuthDAO;
import handler.Unauthorized;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogoutServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successLogout() {
        UserData userData = new UserData("username", "password", "email");
        AuthData authData = registerUser(userData);
        String authToken = authData.authToken();

        new LogoutService().logout(authToken);

        AuthDataAccess authDAO = new MemoryAuthDAO();
        Assertions.assertNull(authDAO.getAuthByToken(authToken));
    }

    private AuthData registerUser(UserData userData) {
        return new RegisterService().register(userData);
    }

    @Test
    void invalidAuthToken() {
        Assertions.assertThrowsExactly(Unauthorized.class, () -> {
            String incorrectAuthToken = "notRegistered";

            new LogoutService().logout(incorrectAuthToken);
        });
    }
}
