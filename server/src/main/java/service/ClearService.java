package service;

import data.access.*;

public class ClearService extends Service{
    public void clearDB() {
        UserDataAccess userDAO = new MemoryUserDAO();
        AuthDataAccess authDAO = new MemoryAuthDAO();
        GameDataAccess gameDAO = new MemoryGameDAO();

        userDAO.clearUsers();
        authDAO.clearAuths();
        gameDAO.clearGames();
    }
}
