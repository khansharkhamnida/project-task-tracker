package org.tasktracker.manager;

public class LoginManager {
    private final String validUsername = "admin";
    private final String validPassword = "password123";

    public boolean login(String username, String password) {
        return validUsername.equals(username) && validPassword.equals(password);
    }
}
