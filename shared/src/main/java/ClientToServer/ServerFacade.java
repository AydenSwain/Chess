package ClientToServer;

import com.google.gson.Gson;
import model.*;

import java.io.*;
import java.net.*;
import java.util.Collection;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void clearDB() {
        this.makeRequest("DELETE", "/db", null, null, null);
    }

    public AuthData register(UserData userData) {
        return this.makeRequest("POST", "/user", null, userData, AuthData.class);
    }

    public AuthData login(UserData userData) {
        return this.makeRequest("POST", "/session", null, userData, AuthData.class);
    }

    public void logout(AuthData authData) {
        this.makeRequest("DELETE", "/session", authData, null, null);
    }

    public Collection<GameData> listGames(AuthData authData) {
        GameList gameList = this.makeRequest("GET", "/game", authData, null, GameList.class);
        return gameList.games();
    }

    public GameData createGame(AuthData authData,GameData gameData) {
        return this.makeRequest("POST", "/game", authData, gameData, GameData.class);
    }

    public void joinGame(AuthData authData, PlayerData playerData) {
        this.makeRequest("PUT", "/game", authData, playerData, null);
    }

    private <T, R> R makeRequest(String method, String path, AuthData authHeader, T bodyObject, Class<R> expectedClass) {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (authHeader != null) {
                http.addRequestProperty("authorization", authHeader.authToken());
            }

            if (bodyObject != null) {
                writeBody(bodyObject, http);
            }

            http.connect();
            throwIfNotSuccessful(http);

            if (expectedClass != null) {
                return readBody(http, expectedClass);
            }

            return null;

        } catch (ResponseException e) {
            throw e;

        } catch (Exception e) {
            throw new ResponseException(500, e.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");

            String reqData = new Gson().toJson(request);

            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var responseCode = http.getResponseCode();

        if (!isSuccessful(responseCode)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    InputStreamReader reader = new InputStreamReader(respErr);
                    ResponseExceptionData responseExceptionData =  new Gson().fromJson(reader, ResponseExceptionData.class);

                    throw new ResponseException(responseCode, responseExceptionData.message());
                }
            }

            throw new ResponseException(responseCode, "other failure: " + responseCode);
        }
    }

    private boolean isSuccessful(int responseCode) {
        return responseCode / 100 == 2;
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;

        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(respBody);

            response = new Gson().fromJson(reader, responseClass);
        }

        return response;
    }
}
