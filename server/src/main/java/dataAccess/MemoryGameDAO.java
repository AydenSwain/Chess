package dataAccess;

import model.GameData;

import java.util.Collection;
import java.util.LinkedHashMap;

public class MemoryGameDAO implements GameDataAccess{
    private static final LinkedHashMap <Integer, GameData> games = new LinkedHashMap<>();

    @Override
    public Collection<GameData> listGames() {
        return games.values();
    }

    @Override
    public void putGame(GameData gameData) {
        games.put(gameData.gameId(), gameData);
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public void clearGames() {
        games.clear();
    }
}
