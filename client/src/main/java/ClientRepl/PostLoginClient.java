package ClientRepl;

import java.util.Arrays;
import ClientToServer.ResponseException;
import ClientToServer.ServerFacade;
import model.UserData;

public class PostLoginClient implements Client{
    private final ServerFacade facade;

    public PostLoginClient(ServerFacade facade) {
        this.facade = facade;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            String command = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (command) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String login(String[] params) {
        if (params.length == 3) {
            String username = params[0];
            String password = params[1];
            String email = params[2];

            UserData userData = new UserData(username, password, email);
            facade.login(userData);

            return "Logged in as \"" + username + "\"";
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String register(String[] params) {
        if (params.length == 3) {
            String username = params[0];
            String password = params[1];
            String email = params[2];

            UserData userData = new UserData(username, password, email);
            facade.register(userData);

            return "Logged in as \"" + username + "\"";
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String help() {
        return """
            "login <username> <password> <email>" <- To login
            "register <username> <password> <email>" <- To register
            "quit" <- To quit""";
    }
}