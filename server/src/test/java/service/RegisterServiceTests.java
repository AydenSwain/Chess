package service;

import handler.BadRequest;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successRegister() {
        UserData userData = new UserData("GoodUsername", "OkayPassword", "HopefullyValidEmail");
        AuthData authData = new RegisterService().register(userData);

        Assertions.assertNotNull(authData);
    }

    @Test
    public void invalidUserData() {
        Assertions.assertThrowsExactly(BadRequest.class, () -> {
            UserData userData = new UserData("", "", null);
            AuthData authData = new RegisterService().register(userData);
        });
    }
}
