package service;

import data.access.*;
import model.AuthData;

import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClearServiceTests extends ServiceTests{
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void testClearDB() {
        AuthData authData = registerValidUser();

        GameData gameData = addGameToDB(authData);

        new ClearService().clearDB();

        UserDataAccess userDao = new MemoryUserDAO();
        AuthDataAccess authDao = new MemoryAuthDAO();
        GameDataAccess gameDao = new MemoryGameDAO();

        Assertions.assertNull(userDao.getUser(authData.username()));
        Assertions.assertNull(authDao.getAuth(authData.authToken()));
        Assertions.assertNull(gameDao.getGame(gameData.gameID()));
    }
}
