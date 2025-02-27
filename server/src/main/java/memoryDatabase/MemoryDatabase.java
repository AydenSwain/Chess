package memoryDatabase;

import model.*;

import java.util.HashSet;

public class MemoryDatabase {
    public static boolean isActive = true;

    public static HashSet<UserData> savedUserData = new HashSet<>();
    public static HashSet<AuthData> savedAuthData = new HashSet<>();
    public static HashSet<GameData> savedGameData = new HashSet<>();

    public void clearAll() {
        savedUserData.clear();
        savedAuthData.clear();
        savedGameData.clear();
    }
}
