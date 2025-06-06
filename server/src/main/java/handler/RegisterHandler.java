package handler;

import com.google.gson.JsonSyntaxException;
import spark.Route;
import spark.Request;
import spark.Response;

import model.*;

import service.RegisterService;

public class RegisterHandler extends Handler implements Route {
    @Override
    public Object handle(Request req, Response res) {
        try {
            UserData userData = fromJson(req.body(), UserData.class);
            AuthData authData = new RegisterService().register(userData);
            return toJson(authData);
        } catch (AlreadyTaken e) {
            return errorMessage(res, 403, "already taken");
        } catch (BadRequest | JsonSyntaxException e) {
            return errorMessage(res, 400, "bad request");
        } catch (Exception e) {
            return errorMessage(res, 500, e.getMessage());
        }
    }
}
