package service;

import chess.ChessGame;
import dataaccess.GameDataAccess;
import dataaccess.SQLGameDAO;
import handler.BadRequest;
import model.*;

public class CreateGameService extends Service{
    public GameData createGame(String authToken, GameData gameData) {
        validateGameDataFormat(gameData);

        verifyAuthTokenInDB(authToken);

        GameDataAccess gameDAO = new SQLGameDAO();

        gameDAO.getGame(gameData.gameID());
        return gameDAO.addGame(gameData);
    }

    private void validateGameDataFormat(GameData gameData) {
        if (gameData == null ||
            gameData.gameName() == null || gameData.gameName().isEmpty()) {
            throw new BadRequest("Invalid game data");
        }
    }


}
