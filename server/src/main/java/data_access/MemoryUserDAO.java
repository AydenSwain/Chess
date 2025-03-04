package data_access;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDataAccess{
    private static final HashMap<String, UserData> users = new HashMap<>();

    @Override
    public UserData getUser(String username) {
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
