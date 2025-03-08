package service;

import dataaccess.AuthDataAccess;
import dataaccess.MemoryAuthDAO;

public class LogoutService extends Service{
    public void logout(String authToken) {
        verifyAuthTokenInDB(authToken);

        AuthDataAccess authDAO = new MemoryAuthDAO();
        authDAO.removeAuth(authToken);
    }
}
