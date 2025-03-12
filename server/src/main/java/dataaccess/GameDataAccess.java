package dataaccess;

import model.GameData;
import java.util.Collection;

public interface GameDataAccess {
    public Collection<GameData> listGames();
    public GameData getGame(int gameID);
    public GameData addGame(GameData gameData);
    public void clearGames();
}
