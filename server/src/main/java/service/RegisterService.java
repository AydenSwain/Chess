package service;

import dataaccess.SQLUserDAO;
import dataaccess.UserDataAccess;
import model.*;
import handler.AlreadyTaken;

public class RegisterService extends Service{
    public AuthData register(UserData userData) {
        validateUserDataFormat(userData);

        UserDataAccess userDAO = new SQLUserDAO();

        try {
            if (userDAO.getUser(userData.username()) != null) {
                throw new AlreadyTaken("Username is already taken");
            }
        } catch (dataaccess.DataAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            userDAO.addUser(userData);
        } catch (dataaccess.DataAccessException e) {
            throw new RuntimeException(e);
        }

        return addNewAuthToDB(userData);
    }
}
