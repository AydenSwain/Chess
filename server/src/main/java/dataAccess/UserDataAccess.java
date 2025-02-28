package dataAccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

public interface UserDataAccess {
    public UserData getUserByName(String username);
    public void addUser(UserData user);
    public void clearUsers();
}
