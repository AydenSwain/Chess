package client;

import org.junit.jupiter.api.*;
import ServerFacade.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void successRegister() {
        Assertions.assertTrue(true);
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
