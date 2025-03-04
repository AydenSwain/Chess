package handler;

import service.LogoutService;
import spark.Route;
import spark.Request;
import spark.Response;

public class LogoutHandler extends Handler implements Route {
    @Override
    public Object handle(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            new LogoutService().logout(authToken);
            return "{}";
        } catch (Unauthorized e) {
            return errorMessage(res, 401, "unauthorized");
        } catch (Exception e) {
            return errorMessage(res, 500, e.getMessage());
        }
    }
}
