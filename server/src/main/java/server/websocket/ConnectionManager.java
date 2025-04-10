package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static websocket.messages.ServerMessage.ServerMessageType.*;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String authToken, Session session) {
        Connection connection = new Connection(authToken, session);
        connections.put(authToken, connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void loadGame(ChessGame game, String onlyAuthToken) {
        ServerMessage serverMessage = new ServerMessage(LOAD_GAME, game, null);

        if (onlyAuthToken != null) {
            send(serverMessage, connections.get(onlyAuthToken));
        } else {
            broadcast(null, serverMessage);
        }
    }

    public void error(String message, String authToken) {
        message = "Error: " + message;
        send(new ServerMessage(ERROR, null, message), connections.get(authToken));
    }

    public void notification(String message, String excludeAuthToken) {
        broadcast(excludeAuthToken, new ServerMessage(NOTIFICATION, null, message));
    }

    public void notification(String message, String excludeAuthToken, boolean isGameOver) {
        broadcast(excludeAuthToken, new ServerMessage(NOTIFICATION, null, message, isGameOver));
    }

    private void broadcast(String excludeAuthToken, ServerMessage serverMessage) {
        var removeList = new ArrayList<Connection>();
        for (Connection c : connections.values()) {
            if (c.session().isOpen()) {
                if (!c.authToken().equals(excludeAuthToken)) {
                    send(serverMessage, c);
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open
        for (Connection c : removeList) {
            connections.remove(c.authToken());
        }
    }

    private void send(ServerMessage serverMessage, Connection connection) {
        try {
            String json = new Gson().toJson(serverMessage);
            connection.session().getRemote().sendString(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}