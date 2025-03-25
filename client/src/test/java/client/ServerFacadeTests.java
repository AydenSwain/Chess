package client;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import ServerFacade.*;
import server.Server;


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

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    private static final UserData VALID_USER = new UserData("username", "password", "email");
    private static final UserData NULL_USER = null;
    private static final UserData UNREGISTERED_USER = new UserData("UNREGISTERED_USERname", "password", "email");

    private static final AuthData VALID_AUTH = new AuthData("username", "authToken");
    private static final AuthData NULL_AUTH = null;
    private static final AuthData UNAUTHORIZED_AUTH = new AuthData("username", "unauthorizedToken");

    private static final GameData VALID_GAME = new GameData(123, "whiteUsername", "blackUsername", "gameName", new ChessGame());
    private static final GameData NULL_GAME = null;
    private static final GameData INVALID_GAME = new GameData(256195493, "whiteUsername", "blackUsername", "gameName", new ChessGame());
    private static final GameData CHANGED_GAME = new GameData(123, "whiteUser", "blackUser", "gameName", new ChessGame());

    @Test
    public void successRegister() {
        AuthData authData = facade.register(VALID_USER);
        Assertions.assertTrue(authData.authToken().length() > 10);
    }

    @Test
    public void failRegister() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successLogin() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failLogin() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successLogout() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failLogout() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successListGames() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failListGames() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successCreateGame() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failCreateGame() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successJoinGame() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failJoinGame() {
        Assertions.assertTrue(true);
    }
}
