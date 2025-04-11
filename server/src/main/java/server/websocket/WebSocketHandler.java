package server.websocket;

import chess.ChessMove;
import chess.ChessPiece;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.SQLGameDAO;
import dataaccess.GameDataAccess;
import dataaccess.SQLAuthDAO;
import dataaccess.AuthDataAccess;
import dataaccess.SQLUserDAO;
import dataaccess.UserDataAccess;
import chess.ChessGame;
import handler.Unauthorized;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import static chess.ChessGame.TeamColor.*;

@WebSocket
public class WebSocketHandler {
    private final HashMap<Integer, ConnectionManager> gameConnections = new HashMap<>();
    private final UserDataAccess userDAO = new SQLUserDAO();
    private final AuthDataAccess authDAO = new SQLAuthDAO();
    private final GameDataAccess gameDAO = new SQLGameDAO();

    private ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);

        if (!gameConnections.containsKey(userGameCommand.getGameID())) {
            gameConnections.put(userGameCommand.getGameID(), new ConnectionManager());
        }
        connections = gameConnections.get(userGameCommand.getGameID());

        try {
            switch (userGameCommand.getCommandType()) {
                case CONNECT -> connect(userGameCommand, session);
                case MAKE_MOVE -> makeMove(userGameCommand);
                case LEAVE -> leave(userGameCommand);
                case RESIGN -> resign(userGameCommand);
            }

        } catch (Exception e) {
            connections.error(e.getMessage(), userGameCommand.getAuthToken());
        }
    }

    @OnWebSocketError
    public void webSocketError(Session session, Throwable throwable) {
        connections.error(throwable.getMessage(), session);
    }

    private void checkAuthorised(UserGameCommand userGameCommand) {
        if (authDAO.getAuth(userGameCommand.getAuthToken()) == null) {
            throw new RuntimeException("Unauthorized");
        }
    }

    private GameData getGameData(UserGameCommand userGameCommand) {
        GameData gameData = gameDAO.getGame(userGameCommand.getGameID());
        if (gameData == null) {
            throw new RuntimeException("Game not found");
        }
        return gameData;
    }

    private String getUsername(String authToken) {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData == null) {
            throw new RuntimeException("Auth not found");
        }

        return authData.username();
    }

    private void connect(UserGameCommand userGameCommand, Session session) {
        checkAuthorised(userGameCommand);

        String authToken = userGameCommand.getAuthToken();

        connections.add(authToken, session);

        GameData gameData = getGameData(userGameCommand);
        ChessGame chessGame = gameData.game();
        if (chessGame == null) {
            chessGame = new ChessGame();
            chessGame.startGame();
            gameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), chessGame);
            gameDAO.updateGame(gameData);
        }

        connections.loadGame(gameData.game(), authToken);
        String message = String.format("\"%s\" connected", getUsername(authToken));
        connections.notification(message, authToken);
    }

    private void makeMove(UserGameCommand userGameCommand) {
        ChessMove move = userGameCommand.getMove();
        String authToken = userGameCommand.getAuthToken();

        GameData gameData = getGameData(userGameCommand);
        ChessGame chessGame = gameData.game();

        ChessGame.TeamColor color;
        if (gameData.whiteUsername() == null) {
            color = null;
        } else {
            color = (gameData.whiteUsername().equals(getUsername(authToken))) ? WHITE : BLACK;
        }

        if (!chessGame.isInPlay()) {
            throw new RuntimeException("Cannot move after game is over");
        }
        if (chessGame.getTeamTurn() != color) {
            if (color == null) {
                throw new RuntimeException("Cannot move as an observer");
            }

            throw new RuntimeException("Cannot move during opponent's turn");
        }
        ChessPiece piece = chessGame.getBoard().getPiece(move.getStartPosition());
        if (piece == null || chessGame.getTeamTurn() != piece.getTeamColor()) {
            throw new RuntimeException("Cannot move opponent's piece");
        }

        Collection<ChessMove> validMoves = chessGame.validMoves(move.getStartPosition());
        if (!validMoves.contains(move)) {
            throw new RuntimeException("Invalid move");
        }

        try {
            chessGame.makeMove(move);
        } catch (InvalidMoveException e) {
            throw new RuntimeException("Invalid move");
        }
        gameDAO.updateGame(gameData);

        connections.loadGame(chessGame, null);
        String message = String.format("\"%s\" made move: %s", getUsername(authToken), move.toString());
        connections.notification(message, authToken);

        if (chessGame.isInCheckmate(color)) {
            String otherPlayerName = getOtherPlayerName(gameData);
            message = String.format("\"%s\" is in checkmate. Great job!", otherPlayerName);
            connections.notification(message, null);
        } else if (chessGame.isInCheck(color)) {
            String otherPlayerName = getOtherPlayerName(gameData);
            message = String.format("\"%s\" is in check!", otherPlayerName);
            connections.notification(message, null);
        } else if (chessGame.isInStalemate(color)) {
            String otherPlayerName = getOtherPlayerName(gameData);
            message = String.format("\"%s\" is in stalemate!", otherPlayerName);
            connections.notification(message, null);
        }
    }

    private String getOtherPlayerName(GameData gameData) {
        if (gameData.game().getTeamTurn() == WHITE) {
            return gameData.blackUsername();
        }
        return gameData.whiteUsername();
    }

    private void leave(UserGameCommand userGameCommand) {
        String authToken = userGameCommand.getAuthToken();
        String username = getUsername(authToken);

        connections.remove(authToken);

        GameData gameData = getGameData(userGameCommand);
        if (Objects.equals(gameData.whiteUsername(), username)) {
            gameData = new GameData(gameData.gameID(), null, gameData.blackUsername(), gameData.gameName(), gameData.game());
            gameDAO.updateGame(gameData);
        } else if (Objects.equals(gameData.blackUsername(), username)) {
            gameData = new GameData(gameData.gameID(), gameData.whiteUsername(), null, gameData.gameName(), gameData.game());
            gameDAO.updateGame(gameData);
        }

        String message = String.format("\"%s\" left the game", getUsername(authToken));
        connections.notification(message, authToken);
    }

    private void resign(UserGameCommand userGameCommand) {
        GameData gameData = getGameData(userGameCommand);
        ChessGame chessGame = gameData.game();
        String username = getUsername(userGameCommand.getAuthToken());

        if (!Objects.equals(gameData.whiteUsername(), username) && !Objects.equals(gameData.blackUsername(), username)) {
            throw new RuntimeException("Cannot resign as an observer");
        }
        if (!chessGame.isInPlay()) {
            throw new RuntimeException("Cannot resign after game is over");
        }

        chessGame.gameOver();
        gameDAO.updateGame(gameData);

        String message = String.format("\"%s\" resigned", getUsername(userGameCommand.getAuthToken()));
        connections.notification(message, null, true);
    }
}