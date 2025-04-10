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

    public void add(String username, Session session) {
        Connection connection = new Connection(username, session);
        connections.put(username, connection);
    }

    public void remove(String username) {
        connections.remove(username);
    }

    public void loadGame(ChessGame game, String username) {
        ServerMessage serverMessage = new ServerMessage(LOAD_GAME, game, null);

        if (username != null) {
            send(serverMessage, connections.get(username));
        } else {
            broadcast(null, serverMessage);
        }
    }

    public void broadcast(String excludeUsername, ServerMessage serverMessage) {
        var removeList = new ArrayList<Connection>();
        for (Connection c : connections.values()) {
            if (c.session().isOpen()) {
                if (!c.username().equals(excludeUsername)) {
                    send(serverMessage, c);
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open
        for (Connection c : removeList) {
            connections.remove(c.username());
        }
    }

    private void send(ServerMessage serverMessage, Connection connection) {
        try {
            connection.session().getRemote().sendString(new Gson().toJson(serverMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}