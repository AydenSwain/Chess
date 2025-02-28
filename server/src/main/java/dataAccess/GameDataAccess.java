package dataAccess;

import model.GameData;

public interface GameDataAccess {
    public Collection<GameData> listGames();
    public void addGame(GameData game);
    public void updateGame(int oldGameId, GameData newGameData);
    public void clearGames();
}
