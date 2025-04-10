package clientrepl;

import HTTPFacade.ResponseException;
import HTTPFacade.ServerFacade;
import websocket.*;

import java.util.Arrays;

public class InGameClient implements Client {
    private final ServerFacade facade;

    private WebSocketFacade loadGameFacade = new WebSocketFacade(new LoadGameHandler());
    private WebSocketFacade notificationFacade = new WebSocketFacade(new NotificationHandler());
    private WebSocketFacade errorFacade = new WebSocketFacade(new ErrorHandler());

    public InGameClient(ServerFacade facade) {
        this.facade = facade;
        String url = facade.getServerUrl();

        loadGameFacade = new WebSocketFacade(url, new LoadGameHandler());
        notificationFacade = new WebSocketFacade(url, new NotificationHandler());
        errorFacade = new WebSocketFacade(url, new ErrorHandler());
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            String command = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (command) {
                case "help" -> help();
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "make_move" -> makeMove(params);
                case "resign" -> resign();
                case "highlight_moves" -> highlightMoves(params);
                case "quit" -> "";
                default -> "Type \"help\" for help!";
            };
        } catch (NumberFormatException e) {
            return "Invalid number format:\n" + e.getMessage();
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    private String redraw() {
    }

    private String leave() {
    }

    private String makeMove(String[] params) {
    }

    private String resign() {
    }

    private String highlightMoves(String[] params) {
    }

    private String help() {
        return """
            "redraw" <- To redraw board
            "leave" <- To leave game
            "make_move <row_number> <col_letter> -> <row_number> <col_letter>" <- To move a piece
            "resign" <- To formally give up
            "highlight_moves <row_number> <col_letter>" <- To highlight the legal moves for a piece
            "quit" <- To quit""";
    }
}
