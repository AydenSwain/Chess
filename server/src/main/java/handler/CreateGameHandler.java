package handler;

import model.GameData;
import model.GameID;
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

            GameID newGameID = new GameID(newGameData.gameID());
            return toJson(newGameID);
        } catch (Unauthorized e) {
            return errorMessage(res, 401, "unauthorized");
        } catch (Exception e) {
            return errorMessage(res, 500, e.getMessage());
        }
    }
}
