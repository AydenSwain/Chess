package service;

import model.*;
import handler.AlreadyTaken;
import dataAccess.*;

public class RegisterService extends Service{
    public AuthData register(UserData userData) {
        validateUserDataFormat(userData);

        UserDataAccess userDAO = new MemoryUserDAO();

        if (userDAO.getUser(userData.username()) != null) {
            throw new AlreadyTaken("Username is already taken");
        }

        userDAO.addUser(userData);

        return addNewAuthToDB(userData);
    }
}
