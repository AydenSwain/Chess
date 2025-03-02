package dataAccess;

import model.AuthData;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDataAccess{
    private static final HashSet<AuthData> auths = new HashSet<>();

    @Override
    public boolean containsAuth(AuthData auth) {
        return auths.contains(auth);
    }

    @Override
    public void addAuth(AuthData auth) {
        auths.add(auth);
    }

    @Override
    public void clearAuths() {
        auths.clear();
    }
}
