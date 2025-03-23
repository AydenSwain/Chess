package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.LinkedHashMap;

public class MemoryGameDAO implements GameDataAccess{
    private static final LinkedHashMap<Integer, GameData> GAMES = new LinkedHashMap<>();

    @Override
    public Collection<GameData> listGames() {
        return GAMES.values();
    }

    @Override
    public GameData getGame(int gameID) {
        return GAMES.get(gameID);
    }

    @Override
    public GameData addGame(GameData gameData) {
        gameData = giveID(gameData);

        GAMES.put(gameData.gameID(), gameData);
        return gameData;
    }

    @Override
    public void updateGame(GameData gameData) {
        GAMES.put(gameData.gameID(), gameData);
    }

    private static int idCounter = 1;
    private GameData giveID(GameData gameData) {
        return new GameData(idCounter++, gameData.whiteUsername(), gameData.blackUsername(),
                            gameData.gameName(), gameData.game());
    }

    @Override
    public void clearGames() {
        GAMES.clear();
    }
}
