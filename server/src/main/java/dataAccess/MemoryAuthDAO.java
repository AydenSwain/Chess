package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDataAccess{
    // HashMap of tokens and AuthData
    private static final HashMap<String, AuthData> auths = new HashMap<>();

    @Override
    public AuthData getAuth(String authToken) {
        return auths.get(authToken);
    }

    @Override
    public void addAuth(AuthData authData) {
        auths.put(authData.authToken(), authData);
    }

    @Override
    public void removeAuth(String authToken) {
        auths.remove(authToken);
    }

    @Override
    public void clearAuths() {
        auths.clear();
    }
}
