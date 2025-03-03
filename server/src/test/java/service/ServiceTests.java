package service;

import model.AuthData;
import model.UserData;

public class ServiceTests {
    protected UserData validUserData = new UserData("username", "password", "email");

    protected AuthData registerValidUser() {
        return new RegisterService().register(validUserData);
    }
}
