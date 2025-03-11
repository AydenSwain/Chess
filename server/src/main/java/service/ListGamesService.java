package service;

import dataaccess.GameDataAccess;
import dataaccess.MemoryGameDAO;
import model.GameData;

import java.util.Collection;

public class ListGamesService extends Service{
    public Collection<GameData> listGames(String authToken) {
        verifyAuthTokenInDB(authToken);

        GameDataAccess gameDAO = new MemoryGameDAO();
        return gameDAO.listGames();
    }
}
