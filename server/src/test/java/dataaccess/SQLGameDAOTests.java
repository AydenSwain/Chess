package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.GameData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDAOTests {
    private static final GameData VALID_GAME = new GameData(123, "whiteUsername", "blackUsername", "gameName", new ChessGame());
    private static final GameData NULL_GAME = null;
    private static final GameData INVALID_GAME = new GameData(256195493, "whiteUsername", "blackUsername", "gameName", new ChessGame());
    private static final GameData CHANGED_GAME = new GameData(123, "whiteUser", "blackUser", "gameName", new ChessGame());

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
            gameDAO.addGame(VALID_GAME);
        });
    }

    @Test
    public void nullGame() {
        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            gameDAO.addGame(NULL_GAME);
        });
    }

    @Test
    public void successGetGame() {
        gameDAO.addGame(VALID_GAME);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.getGame(VALID_GAME.gameID());
        });
    }

    @Test
    public void invalidGameID() {
        gameDAO.addGame(VALID_GAME);

        Assertions.assertNull(gameDAO.getGame(INVALID_GAME.gameID()));
    }

    @Test
    public void successListGames() {
        gameDAO.addGame(VALID_GAME);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.listGames();
        });
    }

    @Test
    public void noGames() {
        Assertions.assertEquals(gameDAO.listGames(), new ArrayList<>());
    }

    @Test
    public void successUpdateGame() {
        gameDAO.addGame(VALID_GAME);

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.updateGame(CHANGED_GAME);
        });
    }

    @Test
    public void nullUpdateGame() {
        gameDAO.addGame(VALID_GAME);

        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            gameDAO.updateGame(NULL_GAME);
        });
    }

    @Test
    public void successClearGames() {
        Assertions.assertDoesNotThrow(() -> {
            gameDAO.clearGames();
        });
    }
}
