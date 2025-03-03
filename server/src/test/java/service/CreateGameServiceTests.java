package service;

import handler.BadRequest;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateGameServiceTests extends ServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successCreateGame() {
        AuthData authData = registerValidUser();
        String authToken = authData.authToken();

        GameData oldGameData = new GameData(0, null, null, "gameName", null);

        GameData newGameData = new CreateGameService().createGame(authToken, oldGameData);

        Assertions.assertNotNull(newGameData);
    }

    @Test
    void missingGameName() {
        AuthData authData = registerValidUser();
        String authToken = authData.authToken();

        Assertions.assertThrowsExactly(BadRequest.class, () -> {
            GameData oldGameData = new GameData(0, null, null, "", null);
            GameData newGameData = new CreateGameService().createGame(authToken, oldGameData);
        });
    }
}
