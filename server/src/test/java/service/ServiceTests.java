package service;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ServiceTests {
    protected UserData validUserData = new UserData("username", "password", "email");

    protected AuthData registerValidUser() {
        return new RegisterService().register(validUserData);
    }

    protected GameData addGameToDB(AuthData authData) {
        GameData oldGameData = new GameData(0, null, null, "gameName", null);
        return new CreateGameService().createGame(authData.authToken(), oldGameData);
    }
}
