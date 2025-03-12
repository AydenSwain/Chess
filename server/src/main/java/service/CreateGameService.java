package service;

import chess.ChessGame;
import dataaccess.GameDataAccess;
import dataaccess.SQLGameDAO;
import handler.BadRequest;
import model.*;

public class CreateGameService extends Service{
    public GameData createGame(String authToken, GameData oldGameData) {
        validateGameDataFormat(oldGameData);

        verifyAuthTokenInDB(authToken);

        GameData newGameData =
                new GameData(generateID(), oldGameData.whiteUsername(), oldGameData.blackUsername(), oldGameData.gameName(), new ChessGame());

        GameDataAccess gameDAO = new SQLGameDAO();
        gameDAO.addGame(newGameData);

        return newGameData;
    }

    private void validateGameDataFormat(GameData gameData) {
        if (gameData == null ||
            gameData.gameName() == null || gameData.gameName().isEmpty()) {
            throw new BadRequest("Invalid game data");
        }
    }

    private static int idCounter = 37828324;
    protected int generateID() {
        return idCounter++;
    }
}
