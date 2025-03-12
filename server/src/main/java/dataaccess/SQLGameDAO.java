package dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static dataaccess.DatabaseManager.configureDatabase;

public class SQLGameDAO implements GameDataAccess {
    public SQLGameDAO() {
        String createStatement = """
                CREATE TABLE IF NOT EXISTS  games (
                  `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                  `gameJson` VARCHAR(2000) NOT NULL
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
    public void addGame(GameData gameData) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "INSERT INTO games (gameJson) VALUES (?)";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                String json = toJson(gameData);
                ps.setString(1, json);

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
            throw new DataAccessException("Unable to clear auth data: " + ex.getMessage());
        }
    }
}