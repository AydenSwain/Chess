package dataAccess;

import model.GameData;
import java.util.Collection;

public interface GameDataAccess {
    public Collection<GameData> listGames();
    public GameData getGame(int gameID);
    public void addGame(GameData gameData);
    public void clearGames();
}
