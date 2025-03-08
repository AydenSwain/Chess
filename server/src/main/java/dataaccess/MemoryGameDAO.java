package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.LinkedHashMap;

public class MemoryGameDAO implements GameDataAccess{
    private static final LinkedHashMap <Integer, GameData> GAMES = new LinkedHashMap<>();

    @Override
    public Collection<GameData> listGames() {
        return GAMES.values();
    }

    @Override
    public GameData getGame(int gameID) {
        return GAMES.get(gameID);
    }

    @Override
    public void addGame(GameData gameData) {
        GAMES.put(gameData.gameID(), gameData);
    }

    @Override
    public void clearGames() {
        GAMES.clear();
    }
}
