package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Date;

/**
 * Класс сессии клиентов
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


    private String token;
    private SessionType sessionType;
    private Long clientId;
    private Date expDate;

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

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "token='" + token + '\'' +
                ", sessionType=" + sessionType +
                ", clientId=" + clientId +
                ", expDate=" + expDate +
                '}';
    }
}
