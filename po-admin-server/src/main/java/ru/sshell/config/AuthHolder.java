package ru.sshell.config;

import ru.sshell.model.SessionData;

public class AuthHolder {

    private AuthHolder() {}

    private static ThreadLocal<SessionData> authDataHolder = new ThreadLocal<>();

    public static void setAuthData(SessionData authData) {
        authDataHolder.set(authData);
    }

    public static SessionData getAuthData() {
        return authDataHolder.get();
    }

    public static void removeAuthData() {
        authDataHolder.remove();
    }

}
