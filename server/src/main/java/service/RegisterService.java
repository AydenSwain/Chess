package service;

import model.*;
import handler.UsernameAlreadyTaken;
import dataAccess.*;

public class RegisterService extends Service{
    public AuthData register(UserData userData) throws UsernameAlreadyTaken {
        MemoryUserDAO userDAO = new MemoryUserDAO();

        if (userDAO.getUserByName(userData.username()) != null) {
            throw new UsernameAlreadyTaken();
        }

        userDAO.addUser(userData);

        AuthData authData = generateAuthData(userData.username());
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        authDAO.addAuth(authData);
        return authData;
    }
}
