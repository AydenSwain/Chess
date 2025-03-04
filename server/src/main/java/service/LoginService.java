package service;

import handler.Unauthorized;
import model.*;
import dataAccess.*;

import java.util.Objects;

public class LoginService extends Service{
    public AuthData login(UserData userData) {
        validateUserDataFormat(userData);

        verifyUser(userData);

        return addNewAuthToDB(userData);
    }

    private void verifyUser(UserData userData) {
        MemoryUserDAO userDAO = new MemoryUserDAO();
        UserData dbUserData = userDAO.getUser(userData.username());

        if (dbUserData == null) {
            throw new Unauthorized("User not registered");
        }

        if (!(Objects.equals(dbUserData.password(), userData.password()))) {
            throw new Unauthorized("Wrong password");
        }
    }

}
