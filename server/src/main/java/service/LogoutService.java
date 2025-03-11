package service;

import dataaccess.AuthDataAccess;
import dataaccess.SQLAuthDAO;

public class LogoutService extends Service{
    public void logout(String authToken) {
        verifyAuthTokenInDB(authToken);

        AuthDataAccess authDAO = new SQLAuthDAO();
        authDAO.removeAuth(authToken);
    }
}
