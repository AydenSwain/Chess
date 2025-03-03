package service;

import handler.BadRequest;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginServiceTests extends ServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successLogin() {
        registerValidUser();

        AuthData authData = new LoginService().login(validUserData);

        Assertions.assertNotNull(authData);
    }

    @Test
    void invalidPassword() {
        registerValidUser();

        Assertions.assertThrowsExactly(BadRequest.class, () -> {
            UserData incorrectUserData = new UserData("username", "", "email");
            AuthData authData = new LoginService().login(incorrectUserData);
        });
    }
}
