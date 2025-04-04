package service;

import dataaccess.*;

public class ClearService extends Service{
    public void clearDB() {
        UserDataAccess userDAO = new SQLUserDAO();
        AuthDataAccess authDAO = new SQLAuthDAO();
        GameDataAccess gameDAO = new SQLGameDAO();

        try {
            userDAO.clearUsers();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        authDAO.clearAuths();
        gameDAO.clearGames();
    }
}
