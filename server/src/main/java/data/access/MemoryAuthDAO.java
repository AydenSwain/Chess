package data.access;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDataAccess{
    private static final HashMap<String, AuthData> AUTHS = new HashMap<>();

    @Override
    public AuthData getAuth(String authToken) {
        return AUTHS.get(authToken);
    }

    @Override
    public void addAuth(AuthData authData) {
        AUTHS.put(authData.authToken(), authData);
    }

    @Override
    public void removeAuth(String authToken) {
        AUTHS.remove(authToken);
    }

    @Override
    public void clearAuths() {
        AUTHS.clear();
    }
}
