package websocket.messages;

import chess.ChessGame;

import java.util.Objects;

/**
 * Represents a Message the server can send through a WebSocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class ServerMessage {
    private final ServerMessageType serverMessageType;
    private final ChessGame game;
    private final String message;

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessage(ServerMessageType type) {
        this.serverMessageType = type;
    }

    public ServerMessage(ServerMessageType serverMessageType, ChessGame game, String message) {
        this.serverMessageType = serverMessageType;
        this.game = game;
        this.message = message;
    }

    public ServerMessageType getServerMessageType() {
        return serverMessageType;
    }

    public ChessGame getGame() {
        return game;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerMessage that = (ServerMessage) o;
        return serverMessageType == that.serverMessageType && Objects.equals(game, that.game) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverMessageType, game, message);
    }
}
