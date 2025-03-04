package service;

import handler.Unauthorized;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListGamesServiceTests extends ServiceTests {
    @BeforeEach
    public void beforeEach() {
        new ClearService().clearDB();
    }

    @Test
    public void successListGames() {
        AuthData authData = registerValidUser();

        addGameToDB(authData);

        Assertions.assertDoesNotThrow(() -> {
            new ListGamesService().listGames(authData.authToken());
        });
    }

    @Test
    void invalidAuthToken() {
        AuthData authData = registerValidUser();

        addGameToDB(authData);

        String invalidToken = "invalidToken";

        Assertions.assertThrowsExactly(Unauthorized.class, () -> {
            new ListGamesService().listGames(invalidToken);
        });
    }
}
