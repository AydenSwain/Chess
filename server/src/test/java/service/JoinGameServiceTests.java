package service;

import chess.ChessGame;
import handler.BadRequest;
import model.AuthData;
import model.GameData;
import model.PlayerData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JoinGameServiceTests extends ServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successJoinGame() {
        AuthData authData = registerValidUser();

        GameData gameData = addGameToDB(authData);
        PlayerData playerData = new PlayerData(ChessGame.TeamColor.WHITE, gameData.gameId());

        Assertions.assertDoesNotThrow(() -> {
            new JoinGameService().joinGame(authData.authToken(), playerData);
        });
    }

    @Test
    void invalidGameID() {
        AuthData authData = registerValidUser();

        GameData gameData = addGameToDB(authData);
        PlayerData playerData = new PlayerData(ChessGame.TeamColor.WHITE, 0);

        Assertions.assertThrowsExactly(BadRequest.class, () -> {
            new JoinGameService().joinGame(authData.authToken(), playerData);
        });
    }
}
