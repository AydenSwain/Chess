package service;

import dataaccess.SQLUserDAO;
import dataaccess.UserDataAccess;
import handler.Unauthorized;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class LoginService extends Service{
    public AuthData login(UserData userData) {
        validateUserDataFormat(userData);

        verifyUser(userData);

        return addNewAuthToDB(userData);
    }

    private void verifyUser(UserData userData) {
        UserDataAccess userDAO = new SQLUserDAO();
        UserData dbUserData = null;
        try {
            dbUserData = userDAO.getUser(userData.username());
        } catch (dataaccess.DataAccessException e) {
            throw new RuntimeException(e);
        }

        if (dbUserData == null) {
            throw new Unauthorized("User not registered");
        }

        if (!(BCrypt.checkpw(userData.password(), dbUserData.password()))) {
            throw new Unauthorized("Wrong password");
        }
    }

}
