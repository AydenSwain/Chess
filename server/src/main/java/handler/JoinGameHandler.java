package handler;

import spark.Route;
import spark.Request;
import spark.Response;

import model.*;

import service.JoinGameService;

public class JoinGameHandler extends Handler implements Route {
    public Object handle(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            PlayerData playerData = fromJson(req.body(), PlayerData.class);

            new JoinGameService().joinGame(authToken, playerData);

            return "{}";

        } catch (BadRequest e) {
            return errorMessage(res, 400, "bad request");

        } catch (Unauthorized e) {
            return errorMessage(res, 401, "unauthorized");

        } catch (AlreadyTaken e) {
            return errorMessage(res, 403, "already taken");

        } catch (Exception e) {
            return errorMessage(res, 500, e.getMessage());
        }
    }
}
