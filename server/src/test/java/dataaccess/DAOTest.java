package dataaccess;

import model.UserData;

public class DAOTest {
    protected static UserData validUser = new UserData("username", "password", "email");
    protected static UserData nullUser = null;
    protected static UserData unregisteredUser = new UserData("unregisteredUsername", "password", "email");
}
