package data_access;

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
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public void addGame(GameData gameData) {
        games.put(gameData.gameID(), gameData);
    }

    @Override
    public void clearGames() {
        games.clear();
    }
}
