package service;

import data.access.GameDataAccess;
import data.access.MemoryGameDAO;
import model.GameData;

import java.util.Collection;

public class ListGamesService extends Service{
    public Collection<GameData> listGames(String authToken) {
        verifyAuthTokenInDB(authToken);

        GameDataAccess gameDao = new MemoryGameDAO();
        return gameDao.listGames();
    }
}
