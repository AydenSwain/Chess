package server.websocket;

import com.google.gson.Gson;
import dataaccess.SQLGameDAO;
import dataaccess.GameDataAccess;
import dataaccess.SQLAuthDAO;
import dataaccess.AuthDataAccess;
import dataaccess.SQLUserDAO;
import dataaccess.UserDataAccess;
import handler.Unauthorized;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;

import java.io.IOException;

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

    private void connect(UserGameCommand userGameCommand, Session session) {
        checkAuthorised(userGameCommand);

        connections.add(userGameCommand.getUsername(), session);

        GameData gameData = gameDAO.getGame(userGameCommand.getGameID());
        if (gameData == null) {
            throw new RuntimeException("Game not found");
        }
        connections.loadGame(gameData.game(), userGameCommand.getUsername());
    }
}