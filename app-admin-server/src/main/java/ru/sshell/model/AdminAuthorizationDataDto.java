package ru.sshell.model;

public class AdminAuthorizationDataDto {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public AdminAuthorizationDataDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AdminAuthorizationDataDto setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "AuthorizationDataDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
