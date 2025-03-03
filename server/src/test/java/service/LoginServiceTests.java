package service;

import handler.BadRequest;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successLogin() {
        UserData userData = new UserData("username", "password", "email");
        registerUser(userData);

        AuthData authData = new LoginService().login(userData);

        Assertions.assertNotNull(authData);
    }

    private void registerUser(UserData userData) {
        new RegisterService().register(userData);
    }

    @Test
    void invalidPassword() {
        UserData userData = new UserData("username", "password", "email");
        registerUser(userData);

        Assertions.assertThrowsExactly(BadRequest.class, () -> {
            UserData incorrectUserData = new UserData("username", "", "email");
            AuthData authData = new LoginService().login(incorrectUserData);
        });
    }
}
