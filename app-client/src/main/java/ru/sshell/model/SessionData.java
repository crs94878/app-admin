package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Класс информации по сессии
 */
public class SessionData {

    public enum SessionType {
        ADMIN(1, "admin"), CLIENT(2, "client");

        private final int code;
        private final String description;

        SessionType(int code, String description) {
            this.code = code;
            this.description = description;
        }

        @JsonValue
        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        @JsonCreator
        public static SessionType getTypeByCode(int code) {
            for (SessionType type : SessionType.values()) {
                if (type.code == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Not found session type by code: " + code);
        }


        @Override
        public String toString() {
            return "SessionType{" +
                    "code=" + code +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    private Long clientId;
    private String token;
    private SessionType sessionType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public Long getClientId() {
        return clientId;
    }

    public SessionData setClientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "clientId=" + clientId +
                ", token='" + token + '\'' +
                ", sessionType=" + sessionType +
                '}';
    }
}
