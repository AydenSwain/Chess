package service;

import java.util.UUID;
import model.*;

public class Service {
    protected AuthData generateAuthData(String username) {
         String authToken = UUID.randomUUID().toString();
         return new AuthData(authToken, username);
    }
}
