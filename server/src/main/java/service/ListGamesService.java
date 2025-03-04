package service;

import data_access.GameDataAccess;
import data_access.MemoryGameDAO;
import model.GameData;

import java.util.Collection;

public class ListGamesService extends Service{
    public Collection<GameData> listGames(String authToken) {
        verifyAuthTokenInDB(authToken);

        GameDataAccess gameDao = new MemoryGameDAO();
        return gameDao.listGames();
    }
}
