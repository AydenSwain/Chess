package dataAccess;

import model.GameData;
import java.util.Collection;

public interface GameDataAccess {
    public Collection<GameData> listGames();
    public void putGame(GameData gameData);
    public GameData getGame(int gameID);
    public void clearGames();
}
