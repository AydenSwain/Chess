package service;

import handler.Unauthorized;
import model.*;
import handler.BadRequest;
import dataAccess.*;

public class LogoutService extends Service{
    public void logout(String authToken) {
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        verifyAuth(authDAO, authToken);

        authDAO.removeAuth(authToken);
    }

    private void verifyAuth(AuthDataAccess authDAO, String authToken) {
        if (authDAO.getAuthByToken(authToken) == null) {
            throw new Unauthorized("Invalid auth data");
        }
    }
}
