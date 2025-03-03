package service;

import model.*;
import handler.UsernameAlreadyTaken;
import handler.BadRequest;
import dataAccess.*;

public class RegisterService extends Service{
    public AuthData register(UserData userData) {
        if (isUserDataInvalid(userData)) {
            throw new BadRequest("Invalid user data");
        }

        MemoryUserDAO userDAO = new MemoryUserDAO();

        if (userDAO.getUserByName(userData.username()) != null) {
            throw new UsernameAlreadyTaken("Username is already taken");
        }

        userDAO.addUser(userData);

        AuthData authData = generateAuthData(userData.username());
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        authDAO.addAuth(authData);
        return authData;
    }
}
