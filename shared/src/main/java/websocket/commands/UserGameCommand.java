package websocket.commands;

import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 *
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {
    private final CommandType commandType;
    private final String username;
    private final String authToken;
    private final Integer gameID;
    private final ChessMove move;

    public UserGameCommand(CommandType commandType, String authToken, Integer gameID, ChessMove move) {
        this.commandType = commandType;
        this.authToken = authToken;
        this.gameID = gameID;
    }

    public UserGameCommand(CommandType commandType, String username, String authToken, Integer gameID, ChessMove move) {
        this.commandType = commandType;
        this.username = username;
        this.authToken = authToken;
        this.gameID = gameID;
        this.move = move;
    }

    public enum CommandType {
        CONNECT,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserGameCommand that = (UserGameCommand) o;
        return commandType == that.commandType && Objects.equals(username, that.username) && Objects.equals(authToken, that.authToken) && Objects.equals(gameID, that.gameID) && Objects.equals(move, that.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandType, username, authToken, gameID, move);
    }
}
