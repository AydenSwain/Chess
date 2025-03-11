package dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.UserData;

import java.sql.*;

import static dataaccess.DatabaseManager.configureDatabase;

public class SQLUserDAO implements UserDataAccess {
    public SQLUserDAO() {
        String createStatement = """
                CREATE TABLE IF NOT EXISTS  users (
                  `username` TEXT NOT NULL PRIMARY KEY,
                  `userJson` TEXT NOT NULL
                )
                """;
        configureDatabase(createStatement);
    }

    @Override
    public UserData getUser(String username) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "SELECT userJson FROM users WHERE username = ?";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        String json = rs.getString("userJson");
                        return fromJson(json);

                    } else {
                        return null;
                    }
                }
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to get user data: " + ex.getMessage());
        }
    }

    private UserData fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserData.class);
    }

    @Override
    public void addUser(UserData user) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "INSERT INTO users (username, userJson) VALUES (?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, user.username());

                String json = toJson(user);
                ps.setString(2, json);

                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to add user data: " + ex.getMessage());
        }
    }

    private String toJson(UserData user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(user);
    }

    @Override
    public void clearUsers() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "TRUNCATE TABLE users";

            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Unable to clear user data: " + ex.getMessage());
        }
    }
}