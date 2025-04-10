package server.websocket;

import chess.ChessMove;
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
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static chess.ChessGame.TeamColor.*;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final UserDataAccess userDAO = new SQLUserDAO();
    private final AuthDataAccess authDAO = new SQLAuthDAO();
    private final GameDataAccess gameDAO = new SQLGameDAO();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);

        try {
            switch (userGameCommand.getCommandType()) {
                case CONNECT -> connect(userGameCommand, session);
                case MAKE_MOVE -> makeMove(userGameCommand);
                case LEAVE -> leave(userGameCommand);
                case RESIGN -> resign(userGameCommand);
            }

        } catch (Exception e) {
            connections.error(e.getMessage(), userGameCommand.getUsername());
        }
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

    private void connect(UserGameCommand userGameCommand, Session session) {
        checkAuthorised(userGameCommand);

        connections.add(userGameCommand.getUsername(), session);

        GameData gameData = getGameData(userGameCommand);
        connections.loadGame(gameData.game(), userGameCommand.getUsername());
    }

    private void makeMove(UserGameCommand userGameCommand) {
        checkAuthorised(userGameCommand);

        ChessMove move = userGameCommand.getMove();

        GameData gameData = getGameData(userGameCommand);
        ChessGame chessGame = gameData.game();
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
        String message = userGameCommand.getUsername() + " made move: " + move.toString();
        connections.notification(message, null);

        ChessGame.TeamColor otherPlayerColor = (gameData.game().getTeamTurn() == WHITE) ? BLACK : WHITE;
        if (chessGame.isInCheck(otherPlayerColor)) {
            String otherPlayerName = getOtherPlayerName(gameData);
            String message = otherPlayerName + " is in check!";
            connections.notification(message, null);
        }
        if (chessGame.isInCheckmate(otherPlayerColor)) {
            String otherPlayerName = getOtherPlayerName(gameData);
            String message = otherPlayerName + " is in checkmate. Great job!";
            connections.notification(message, null);
        }
        if (chessGame.isInStalemate(otherPlayerColor)) {
            String otherPlayerName = getOtherPlayerName(gameData);
            String message = otherPlayerName + " is in check!";
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
        checkAuthorised(userGameCommand);

        resign(userGameCommand);

        connections.remove(userGameCommand.getUsername());

        GameData gameData = getGameData(userGameCommand);
        if (Objects.equals(gameData.whiteUsername(), userGameCommand.getUsername())) {
            gameData = new GameData(gameData.gameID(), null, gameData.blackUsername(), gameData.gameName(), gameData.game());
            gameDAO.updateGame(gameData);
        } else if (Objects.equals(gameData.blackUsername(), userGameCommand.getUsername())) {
            gameData = new GameData(gameData.gameID(), gameData.whiteUsername(), null, gameData.gameName(), gameData.game());
            gameDAO.updateGame(gameData);
        }

        String message = userGameCommand.getUsername() + " left the game";
        connections.notification(message, userGameCommand.getUsername());
    }

    private void resign(UserGameCommand userGameCommand) {
        checkAuthorised(userGameCommand);

        GameData gameData = getGameData(userGameCommand);
        ChessGame chessGame = gameData.game();
        chessGame.gameOver();
        gameDAO.updateGame(gameData);

        String message = userGameCommand.getUsername() + " resigned";
        connections.notification(message, null);
    }
}