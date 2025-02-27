package handler;

import model.UserData;
import spark.Route;
import spark.Request;
import spark.Response;

public class RegisterHandler extends Handler implements Route {

    public Object handle(Request req, Response res) throws Exception {
        UserData userData = fromJson(req.body(), UserData.class);
        return toJson(userData);
    }
}
