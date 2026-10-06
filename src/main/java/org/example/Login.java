package org.example;

public class Login {
    private String login;
    private String password;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Login(String password) {
        this.password = password;
    }
}
