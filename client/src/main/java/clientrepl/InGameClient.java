package clientrepl;

import chess.ChessPiece;
import httpfacade.ResponseException;
import httpfacade.ServerFacade;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import ui.BoardPrinter;
import websocket.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static chess.ChessPiece.PieceType.*;

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
        this.handler = new WebSocketHandler(this);
        String url = facade.getServerUrl();
        webSocketFacade = new WebSocketFacade(url, handler);

        this.authToken = Repl.clientAuthData.authToken();
        this.gameID = gameID;
        this.color = color;

        webSocketFacade.connect(authToken, gameID);
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
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
                default -> Repl.HELP_MESSAGE;
            };
        } catch (NumberFormatException e) {
            return "Error: Invalid number format:\n" + e.getMessage();
        } catch (ResponseException | WebSocketException e) {
            return e.getMessage();
        }
    }

    private String redraw() {
        new BoardPrinter(game.getBoard()).print(color, null);

        return "Board was redrawn";
    }

    private String leave() {
        webSocketFacade.leave(authToken, gameID);

        Repl.client = new PostLoginClient(facade);

        return "You have left the game";
    }

    private String makeMove(String[] params) {
        if (params.length == 5) {
            ChessMove move = new ChessMove(new ChessPosition(Integer.parseInt(params[0]), getColNum(params[1])),
                                           new ChessPosition(Integer.parseInt(params[3]), getColNum(params[4])));

            webSocketFacade.makeMove(authToken, gameID, move);

            return "Making move: " + move.toString() + " ...";

        } else if (params.length == 6) {
            ChessMove move = new ChessMove(new ChessPosition(Integer.parseInt(params[0]), getColNum(params[1])),
                                           new ChessPosition(Integer.parseInt(params[3]), getColNum(params[4])),
                                           getPromotionPiece(params[5]));

            webSocketFacade.makeMove(authToken, gameID, move);

            return "Making move: " + move.toString() + " ...";
        }
        throw new ResponseException(400, "Expected: <row_number> <col_letter> -> <row_number> <col_letter> <promotion_piece_if_needed>");
    }

    private ChessPiece.PieceType getPromotionPiece(String promotion) {
        return switch (promotion) {
            case "queen" -> QUEEN;
            case "rook" -> ROOK;
            case "knight" -> KNIGHT;
            case "bishop" -> BISHOP;
            default -> throw new ResponseException(400, String.format("Expected: <queen>, <knight>, <bishop>, or <knight> but got \"%s\"", promotion));
        };
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
        };
    }

    private String resign() {
        webSocketFacade.resign(authToken, gameID);

        return "Resigning ...";
    }

    private String highlightMoves(String[] params) {
        if (params.length == 2) {
            ChessPosition position = new ChessPosition(Integer.parseInt(params[0]), getColNum(params[1]));

            Collection<ChessMove> moves = game.validMoves(position);
            HashSet<ChessPosition> positions = new HashSet<>();

            boolean isFirstIteration = true;
            for (ChessMove move : moves) {
                if (isFirstIteration) {
                    positions.add(move.getStartPosition());
                    isFirstIteration = false;
                }
                positions.add(move.getEndPosition());
            }

            new BoardPrinter(game.getBoard()).print(color, positions);

            return "Moves for " + position.toString() + " highlighted";
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
