package dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.AuthData;

import java.sql.*;

import static dataaccess.DatabaseManager.configureDatabase;

public class SQLAuthDAO implements AuthDataAccess {
    public SQLAuthDAO() {
        String createStatement = """
                CREATE TABLE IF NOT EXISTS  auths (
                  `authToken` VARCHAR(100) NOT NULL PRIMARY KEY,
                  `authJson` VARCHAR(500) NOT NULL
                )
                """;

        configureDatabase(createStatement);
    }

    @Override
    public AuthData getAuth(String authToken) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "SELECT authJson FROM auths WHERE authToken = ?";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, authToken);
                System.out.println(authToken);

                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        String json = rs.getString("authJson");
                        return fromJson(json);

                    } else {
                        return null;
                    }
                }
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to get auth data: " + ex.getMessage());
        }
    }

    private AuthData fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AuthData.class);
    }

    @Override
    public void addAuth(AuthData authData) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "INSERT INTO auths (authToken, authJson) VALUES (?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, authData.authToken());
                System.out.println(authData.authToken());

                String json = toJson(authData);
                ps.setString(2, json);

                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to add auth data: " + ex.getMessage());
        }
    }

    private String toJson(AuthData authData) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(authData);
    }

    @Override
    public void removeAuth(String authToken) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "DELETE FROM auths WHERE authToken = ?";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, authToken);

                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to remove auth data: " + ex.getMessage());
        }
    }

    @Override
    public void clearAuths() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "TRUNCATE TABLE auths";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to clear auth data: " + ex.getMessage());
        }
    }
}