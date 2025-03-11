package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDataAccess{
    private static final HashMap<String, UserData> USERS = new HashMap<>();

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return USERS.get(username);
    }

    @Override
    public void addUser(UserData user) throws DataAccessException {
        USERS.put(user.username(), user);
    }

    @Override
    public void clearUsers() throws DataAccessException {
        USERS.clear();
    }
}
