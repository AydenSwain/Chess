package dataaccess;

import model.UserData;

public interface UserDataAccess {
    public UserData getUser(String username);
    public void addUser(UserData user);
    public void clearUsers();
}
