package dataAccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDataAccess{
    private static final HashMap<String, UserData> users = new HashMap<>();

    @Override
    public UserData getUserByName(String username) {
        return users.get(username);
    }

    @Override
    public void addUser(UserData user) {
        users.put(user.username(), user);
    }

    @Override
    public void clearUsers() {
        users.clear();
    }
}
