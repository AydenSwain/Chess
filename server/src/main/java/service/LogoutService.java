package service;

import data_access.*;

public class LogoutService extends Service{
    public void logout(String authToken) {
        verifyAuthTokenInDB(authToken);

        AuthDataAccess authDAO = new MemoryAuthDAO();
        authDAO.removeAuth(authToken);
    }
}
