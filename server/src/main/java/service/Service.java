package service;

import java.util.UUID;
import model.*;

public class Service {
    protected AuthData generateAuthData(String username) {
         String authToken = UUID.randomUUID().toString();
         return new AuthData(username, authToken);
    }

    protected boolean assertValidUserData(UserData userData) {
        return userData.username() != null && !userData.username().isEmpty()
                && userData.password() != null && !userData.password().isEmpty()
                && userData.email() != null && !userData.email().isEmpty();
    }

    protected boolean assertValidAuthData(AuthData authData) {
        return authData.username() != null && !authData.username().isEmpty()
                && authData.authToken() != null && !authData.authToken().isEmpty();
    }
}
