package client;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.PlayerData;
import model.UserData;
import org.junit.jupiter.api.*;
import ServerFacade.*;
import server.Server;

import java.util.Collection;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        int port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        String serverUrl = "http://localhost:" + port;
        facade = new ServerFacade(serverUrl);
    }

    @BeforeEach
    void setUp() {
        facade.clearDB();
        validAuth = facade.register(VALID_USER);
    }

    @AfterAll
    static void stopServer() {
        facade.clearDB();
        server.stop();
    }

    private static final UserData VALID_USER = new UserData("username", "password", "email");
    private static final UserData NULL_USER = null;
    private static final UserData UNREGISTERED_USER = new UserData("unregistered username", "password", "email");

    private static AuthData validAuth;
    private static final AuthData UNAUTHORIZED_AUTH = new AuthData("username", "unauthorizedToken");

    private static final GameData VALID_GAME = new GameData(123, "whiteUsername", "blackUsername", "gameName", new ChessGame());

    @Test
    public void successClearDB() {
        Assertions.assertDoesNotThrow(() -> facade.clearDB());
    }

    @Test
    public void successRegister() {
        AuthData authData = facade.register(UNREGISTERED_USER);
        Assertions.assertTrue(authData.authToken().length() > 10);
    }

    @Test
    public void failRegister() {
        Assertions.assertThrowsExactly(ResponseException.class, () -> facade.register(NULL_USER));
    }

    @Test
    public void successLogin() {
        AuthData authData = facade.login(VALID_USER);
        Assertions.assertTrue(authData.authToken().length() > 10);
    }

    @Test
    public void failLogin() {
        Assertions.assertThrowsExactly(ResponseException.class, () -> facade.login(NULL_USER));
    }

    @Test
    public void successLogout() {
        Assertions.assertDoesNotThrow(() -> facade.logout(validAuth));
    }

    @Test
    public void failLogout() {
        Assertions.assertThrowsExactly(ResponseException.class, () -> facade.logout(UNAUTHORIZED_AUTH));
    }

    @Test
    public void successListGames() {
        facade.createGame(validAuth, VALID_GAME);
        Collection<GameData> games = facade.listGames(validAuth);
        Assertions.assertEquals(1, games.size());
    }

    @Test
    public void failListGames() {
        facade.createGame(validAuth, VALID_GAME);
        Assertions.assertThrowsExactly(ResponseException.class, () -> facade.listGames(UNAUTHORIZED_AUTH));
    }

    @Test
    public void successCreateGame() {
        Assertions.assertDoesNotThrow(() -> facade.createGame(validAuth, VALID_GAME));
    }

    @Test
    public void failCreateGame() {
        Assertions.assertThrowsExactly(ResponseException.class, () -> facade.createGame(UNAUTHORIZED_AUTH, VALID_GAME));
    }

    private PlayerData gameToPlayer(GameData gameData, ChessGame.TeamColor teamColor) {
        return new PlayerData(teamColor, gameData.gameID());
    }

    @Test
    public void successJoinGame() {
        GameData gameData = facade.createGame(validAuth, VALID_GAME);
        PlayerData playerData = gameToPlayer(gameData, ChessGame.TeamColor.WHITE);
        Assertions.assertDoesNotThrow(() -> facade.joinGame(validAuth, playerData));
    }

    @Test
    public void failJoinGame() {
        GameData gameData = facade.createGame(validAuth, VALID_GAME);
        PlayerData playerData = gameToPlayer(gameData, ChessGame.TeamColor.WHITE);

        // Join twice for the same color
        facade.joinGame(validAuth, playerData);
        Assertions.assertThrowsExactly(ResponseException.class, () -> facade.joinGame(validAuth, playerData));
    }
}
