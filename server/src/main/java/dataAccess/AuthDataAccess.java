package dataAccess;

import model.AuthData;

public interface AuthDataAccess {
    public AuthData getAuthByToken(String authToken);
    public void addAuth(AuthData authData);
    public void removeAuth(String authToken);
    public void clearAuths();
}
