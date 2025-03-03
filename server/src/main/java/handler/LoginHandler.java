package handler;

import com.google.gson.JsonSyntaxException;
import spark.Route;
import spark.Request;
import spark.Response;

import model.*;

import service.LoginService;

public class LoginHandler extends Handler implements Route {
    public Object handle(Request req, Response res) {
        try {
            UserData userData = fromJson(req.body(), UserData.class);
            AuthData authData = new LoginService().login(userData);
            return toJson(authData);
        } catch (Unauthorized e) {
            return errorMessage(res, 401, "unauthorized");
        } catch (Exception e) {
            return errorMessage(res, 500, e.getMessage());
        }
    }
}
