package service;

import java.util.UUID;
import model.*;

public class Service {
    protected AuthData generateAuthData(String username) {
         String authToken = UUID.randomUUID().toString();
         return new AuthData(username, authToken);
    }

    protected boolean isUserDataInvalid(UserData userData) {
        return userData == null ||
               userData.username() == null || userData.username().isEmpty() ||
               userData.password() == null || userData.password().isEmpty();
    }
}
