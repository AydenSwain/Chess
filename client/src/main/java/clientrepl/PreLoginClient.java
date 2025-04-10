package clientrepl;

import java.util.Arrays;
import HTTPFacade.ResponseException;
import HTTPFacade.ServerFacade;
import model.UserData;

public class PreLoginClient implements Client{
    private final ServerFacade facade;

    public PreLoginClient(ServerFacade facade) {
        this.facade = facade;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            String command = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (command) {
                case "help" -> help();
                case "login" -> login(params);
                case "register" -> register(params);
                case "quit" -> "";
                default -> "Type \"help\" for help!";
            };
        } catch (NumberFormatException ex) {
            return "Invalid game number format: " + ex.getMessage();

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
            Repl.clientAuthData = facade.login(userData);

            Repl.client = new PostLoginClient(facade);
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
            Repl.clientAuthData = facade.register(userData);

            Repl.client = new PostLoginClient(facade);
            return "Registered and logged in as \"" + username + "\"";
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