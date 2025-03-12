package service;

import dataaccess.GameDataAccess;
import dataaccess.SQLGameDAO;
import model.GameData;

import java.util.Collection;

public class ListGamesService extends Service{
    public Collection<GameData> listGames(String authToken) {
        verifyAuthTokenInDB(authToken);

        GameDataAccess gameDAO = new SQLGameDAO();
        return gameDAO.listGames();
    }
}
