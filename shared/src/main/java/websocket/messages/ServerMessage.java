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
    private ChessGame game;
    private String message;
    private String errorMessage;
    private Boolean isGameOver;

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

    public ServerMessage(ServerMessageType serverMessageType, ChessGame game, String message, boolean isGameOver) {
        this.serverMessageType = serverMessageType;
        this.game = game;
        this.message = message;
        this.isGameOver = isGameOver;
    }

    public void convertToError() {
        this.errorMessage = message;
        this.message = null;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerMessage that = (ServerMessage) o;
        return serverMessageType == that.serverMessageType && Objects.equals(game, that.game) && Objects.equals(message, that.message) &&
                                    Objects.equals(errorMessage, that.errorMessage) && Objects.equals(isGameOver, that.isGameOver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverMessageType, game, message, errorMessage, isGameOver);
    }
}
