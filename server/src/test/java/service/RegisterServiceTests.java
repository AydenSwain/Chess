package service;

import handler.BadRequest;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterServiceTests extends ServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successRegister() {
        AuthData authData = registerValidUser();

        Assertions.assertNotNull(authData);
    }

    @Test
    public void registerInvalidUser() {
        Assertions.assertThrowsExactly(BadRequest.class, () -> {
            UserData userData = new UserData("", "", null);
            AuthData authData = new RegisterService().register(userData);
        });
    }
}
