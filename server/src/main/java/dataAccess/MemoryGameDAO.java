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
    public void addGame(GameData game) {
        games.put(game.gameId(), game);
    }

    @Override
    public void updateGame(int oldGameId, GameData newGameData) {
        games.put(oldGameId, newGameData);
    }

    @Override
    public void clearGames() {
        games.clear();
    }
}
