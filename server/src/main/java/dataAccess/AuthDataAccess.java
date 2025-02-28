package dataAccess;

import model.AuthData;

public interface AuthDataAccess {
    public boolean containsAuth(AuthData auth);
    public void addAuth(AuthData auth);
    public void clearAuths();
}
