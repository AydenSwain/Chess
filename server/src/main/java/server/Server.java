package server;

import spark.*;
import handler.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        createRoutes();

        Spark.awaitInitialization();
        return Spark.port();
    }

    /**
     * Used to create the routes for the server
     */
    private static void createRoutes() {
        Spark.post("/user", new RegisterHandler());

        Spark.post("/session", new LoginHandler());

        Spark.delete("/db", new ClearHandler());
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
