package handler;

import spark.Request;
import spark.Response;
import spark.Route;

import service.ClearService;

public class ClearHandler extends Handler implements Route {
    public Object handle(Request req, Response res)  {
        try {
            new ClearService().clearDB();
            return "{}";
        } catch (Exception e) {
            return errorMessage(res, 500, e.getMessage());
        }
    }
}
