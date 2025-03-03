package service;

import model.*;
import handler.UsernameAlreadyTaken;
import dataAccess.*;

public class RegisterService extends Service{
    public AuthData register(UserData userData) {
        validateUserDataFormat(userData);

        MemoryUserDAO userDAO = new MemoryUserDAO();

        if (userDAO.getUserByName(userData.username()) != null) {
            throw new UsernameAlreadyTaken("Username is already taken");
        }

        userDAO.addUser(userData);

        return addNewAuthToDB(userData);
    }
}
