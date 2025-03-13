package dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static dataaccess.DatabaseManager.configureDatabase;

public class SQLGameDAO implements GameDataAccess {
    public SQLGameDAO() {
        String createStatement = """
                CREATE TABLE IF NOT EXISTS  games (
                  `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                  `gameJson` TEXT NOT NULL
                )
                """;

        configureDatabase(createStatement);
    }

    @Override
    public Collection<GameData> listGames() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "SELECT gameJson FROM games";

            ArrayList<GameData> games = new ArrayList<>();

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                        String json = rs.getString("gameJson");
                        games.add(fromJson(json));
                    }

                    return games;
                }
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to list games: " + ex.getMessage());
        }
    }

    @Override
    public GameData getGame(int gameID) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "SELECT gameJson FROM games WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setInt(1, gameID);

                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        String json = rs.getString("gameJson");
                        return fromJson(json);

                    } else {
                        return null;
                    }
                }
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to get game data: " + ex.getMessage());
        }
    }

    private GameData fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GameData.class);
    }

    @Override
    public GameData addGame(GameData gameData) {
        try (Connection conn = DatabaseManager.getConnection()) {
                String statement = "INSERT INTO games (gameJson) VALUES (?)";

                try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                    String json = toJson(gameData);

                    ps.setString(1, json);

                    if (ps.executeUpdate() == 1) {
                        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                            generatedKeys.next();
                            int newID = generatedKeys.getInt(1);
                            GameData newGameData = new GameData(newID, gameData.whiteUsername(), gameData.blackUsername(),
                                    gameData.gameName(), gameData.game());
                            updateGame(newGameData);

                            return newGameData;
                        }

                    } else {
                        throw new DataAccessException("Unable to add game data: More than one row effected");
                    }
                }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to add game data: " + ex.getMessage());
        }
    }

    @Override
    public void updateGame(GameData gameData) {
        try (Connection conn = DatabaseManager.getConnection()) {

            String statement = "UPDATE games SET gameJson = ? WHERE id = ?;";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                String json = toJson(gameData);


                ps.setString(1, json);
                ps.setInt(2, gameData.gameID());

                ps.executeUpdate();

            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to add game data: " + ex.getMessage());
        }
    }

    private String toJson(GameData gameData) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(gameData);
    }

    @Override
    public void clearGames() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "TRUNCATE TABLE games";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to clear game data: " + ex.getMessage());
        }
    }
}