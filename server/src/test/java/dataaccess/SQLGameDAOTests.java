package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.GameData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLGameDAOTests {
    private static final GameData validGame = new GameData(123, "whiteUsername", "blackUsername", "gameName", new ChessGame());
    private static final GameData nullGame = null;

    private static SQLGameDAO gameDAO = null;
    private Connection conn = null;

    @BeforeAll
    public static void createDatabase() {
        gameDAO = new SQLGameDAO();
        gameDAO.clearGames();
    }

    @BeforeEach
    public void getConnection() {
        conn = DatabaseManager.getConnection();
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        conn.close();
        conn = null;

        gameDAO.clearGames();
    }

    @Test
    public void successAddGame() {
        Assertions.assertDoesNotThrow(() -> {
            gameDAO.addGame(validGame);
        });
    }

    @Test
    public void nullGame() {
        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            gameDAO.addGame(nullGame);
        });
    }

    @Test
    public void successGetGame() {
        gameDAO.addGame(validGame);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.getGame(validGame.gameID());
        });
    }

//    @Test
//    public void getUnauthorizedGame() {
//        gameDAO.addGame(validGame);
//
//        Assertions.assertNull(gameDAO.getGame(unauthorizedGame.authToken()));
//    }

    @Test
    public void successClearGames() {
        Assertions.assertDoesNotThrow(() -> {
            gameDAO.clearGames();
        });
    }
}
