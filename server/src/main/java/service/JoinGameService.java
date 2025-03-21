package service;

import dataaccess.AuthDataAccess;
import dataaccess.GameDataAccess;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import handler.AlreadyTaken;
import handler.BadRequest;
import model.AuthData;
import model.GameData;
import model.PlayerData;
import chess.ChessGame;

public class JoinGameService extends Service {
    public void joinGame(String authToken, PlayerData playerData) {
        validatePlayerDataFormat(playerData);

        verifyAuthTokenInDB(authToken);

        GameDataAccess gameDAO = new SQLGameDAO();
        GameData gameData = gameDAO.getGame(playerData.gameID());

        if (isPlayerTaken(playerData.playerColor(), gameData)) {
            throw new AlreadyTaken("Color is already taken");
        }

        String username = getUsernameByToken(authToken);

        GameData newGameData = updateGameUsername(username, playerData.playerColor(), gameData);

        gameDAO.updateGame(newGameData);
    }

    private void validatePlayerDataFormat(PlayerData playerData) {
        if (playerData == null || playerData.playerColor() == null || playerData.gameID() == 0) {
            throw new BadRequest("Invalid player data");
        }
    }

    private boolean isPlayerTaken(ChessGame.TeamColor playerColor, GameData gameData) {
        if (playerColor == ChessGame.TeamColor.WHITE) {
            return gameData.whiteUsername() != null;
        }
        return gameData.blackUsername() != null;
    }

    private String getUsernameByToken(String authToken) {
        AuthDataAccess authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.getAuth(authToken);

        return authData.username();
    }

    private GameData updateGameUsername(String username, ChessGame.TeamColor playerColor, GameData oldGameData) {
        String whiteUsername = oldGameData.whiteUsername();
        String blackUsername = oldGameData.blackUsername();

        if (playerColor == ChessGame.TeamColor.WHITE) {
            whiteUsername = username;
        } else {
            blackUsername = username;
        }

        return new GameData(oldGameData.gameID(), whiteUsername, blackUsername, oldGameData.gameName(), oldGameData.game());
    }
}
