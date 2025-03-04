package service;

import java.util.UUID;

import data_access.AuthDataAccess;
import data_access.MemoryAuthDAO;
import handler.BadRequest;
import handler.Unauthorized;
import model.*;

public class Service {
    protected AuthData generateAuthData(String username) {
         String authToken = UUID.randomUUID().toString();
         return new AuthData(username, authToken);
    }

    protected AuthData addNewAuthToDB(UserData userData) {
        AuthData authData = generateAuthData(userData.username());
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        authDAO.addAuth(authData);
        return authData;
    }

    protected void validateUserDataFormat(UserData userData) {
        if (userData == null ||
            userData.username() == null || userData.username().isEmpty() ||
            userData.password() == null || userData.password().isEmpty()) {
            throw new BadRequest("Invalid user data");
        }
    }

    protected void verifyAuthTokenInDB(String authToken) {
        AuthDataAccess authDAO = new MemoryAuthDAO();
        if (authDAO.getAuth(authToken) == null) {
            throw new Unauthorized("Invalid auth data");
        }
    }
}
