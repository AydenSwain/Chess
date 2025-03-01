package server;

import spark.*;
import handler.*;

public class Server {

    public int run(int desiredPort) {
        // Specify the port
        Spark.port(desiredPort);

        // Specify the static files location
        Spark.staticFiles.location("web");

        // Create the routes
        createRoutes();

        // Await initialization
        Spark.awaitInitialization();
        return Spark.port();
    }

    /**
     * Used to create the routes for the server
     */
    private static void createRoutes() {
        Spark.post("/user", new RegisterHandler());

        Spark.delete("/db", new ClearHandler());
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
