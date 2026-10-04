package org.example;

public class CourierWithoutField {
    private String login;
    private String firstName;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public CourierWithoutField(String login, String firstName) {
        this.login = login;
        this.firstName = firstName;
    }
}
