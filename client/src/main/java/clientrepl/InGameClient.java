package clientrepl;

import HTTPFacade.ResponseException;
import HTTPFacade.ServerFacade;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import ui.BoardPrinter;
import websocket.*;

import java.util.Arrays;

import static chess.ChessGame.TeamColor.*;

public class InGameClient implements Client {
    private final ServerFacade facade;
    private final WebSocketHandler handler;
    private final WebSocketFacade webSocketFacade;

    private final String authToken;
    private final int gameID;
    private final ChessGame.TeamColor color;

    public ChessGame game;

    public InGameClient(ServerFacade facade, int gameID, ChessGame.TeamColor color) {
        this.facade = facade;
        this.handler = new WebSocketHandler(game);
        String url = facade.getServerUrl();
        webSocketFacade = new WebSocketFacade(url, handler);

        this.authToken = Repl.clientAuthData.authToken();
        this.gameID = gameID;
        this.color = color;

        webSocketFacade.connect(authToken, gameID);
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

    private void printBoard(ChessGame.TeamColor color) {
        new BoardPrinter(game.getBoard()).print(color);
    }

    private String redraw() {
        printBoard(color);

        return "Board redrawn!";
    }

    private String leave() {
        webSocketFacade.leave(authToken, gameID);

        Repl.client = new PostLoginClient(facade);
    }

    private String makeMove(String[] params) {
        if (params.length == 6) {
            ChessMove move = new ChessMove(new ChessPosition(Integer.parseInt(params[0]), getColNum(params[1])),
                                           new ChessPosition(Integer.parseInt(params[3]), getColNum(params[4])));

            webSocketFacade.makeMove(authToken, gameID, move);
        }
        throw new ResponseException(400, "Expected: <row_number> <col_letter> -> <row_number> <col_letter>");
    }

    private int getColNum(String letter) {
        return switch (letter) {
            case "a" -> 1;
            case "b" -> 2;
            case "c" -> 3;
            case "d" -> 4;
            case "e" -> 5;
            case "f" -> 6;
            case "g" -> 7;
            case "h" -> 8;
            default -> 0;
        }
    }

    private String resign() {
        webSocketFacade.resign(authToken, gameID);
    }

    private String highlightMoves(String[] params) {
        if (params.length == 3) {
            ChessPosition position = new ChessPosition(Integer.parseInt(params[0]), getColNum(params[1]);

            // ---------------------- Add here
            printBoard(color);
        }
        throw new ResponseException(400, "Expected: <row_number> <col_letter>");
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
