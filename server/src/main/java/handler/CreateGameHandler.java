package handler;

import model.GameData;
import service.CreateGameService;
import spark.Route;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends Handler implements Route {
    @Override
    public Object handle(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            GameData oldGameData = fromJson(req.body(), GameData.class);

            GameData newGameData = new CreateGameService().createGame(authToken, oldGameData);

            GameData idGameData = onlyGameID(newGameData);

            return toJson(idGameData);

        } catch (Unauthorized e) {
            return errorMessage(res, 401, "unauthorized");
        } catch (Exception e) {
            return errorMessage(res, 500, e.getMessage());
        }
    }

    private GameData onlyGameID(GameData gameData) {
        return new GameData(gameData.gameID(), null, null, null, null);
    }
}
