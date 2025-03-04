package handler;

import model.GameData;
import model.GameList;
import service.ListGamesService;
import spark.Route;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collection;

public class ListGamesHandler extends Handler implements Route {
    @Override
    public Object handle(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");

            Collection<GameData> gameCollection = new ListGamesService().listGames(authToken);

            Collection<GameData> newGameCollection = removeGameObjs(gameCollection);

            GameList gameList = new GameList(newGameCollection);

            return toJson(gameList);

        } catch (Unauthorized e) {
            return errorMessage(res, 401, "unauthorized");

        } catch (Exception e) {
            return errorMessage(res, 500, e.getMessage());
        }
    }

    private Collection<GameData> removeGameObjs(Collection<GameData> gameCollection) {
        Collection<GameData> newGameCollection = new ArrayList<>();

        for (GameData gameData : gameCollection) {
            GameData newGameData =
                    new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), null);

            newGameCollection.add(newGameData);
        }

        return newGameCollection;
    }
}
