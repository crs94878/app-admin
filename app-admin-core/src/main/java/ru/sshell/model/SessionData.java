package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс сессии клиентов
 */
@ParametersAreNonnullByDefault
public class SessionData {

    @Nonnull
    private final String token;
    @Nonnull
    private final SessionType sessionType;
    @Nonnull
    private final Long userId;
    @Nonnull
    private final Instant expDateTime;

    private SessionData(Builder builder) {
        this.token = Objects.requireNonNull(builder.token, "token");
        this.sessionType = Objects.requireNonNull(builder.sessionType, "sessionType");
        this.userId = Objects.requireNonNull(builder.userId, "userId");
        this.expDateTime = Objects.requireNonNull(builder.expDateTime, "expDateTime");
    }

    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public String getToken() {
        return token;
    }

    @Nonnull
    public SessionType getSessionType() {
        return sessionType;
    }

    @Nonnull
    public Long getUserId() {
        return userId;
    }

    @Nonnull
    public Instant getExpDateTime() {
        return expDateTime;
    }

    public enum SessionType {
        ADMIN("ADMIN"), CLIENT("CLIENT");

        private final static Map<String, SessionType> SESSION_TYPE_MAP;

        static {
            SESSION_TYPE_MAP = Arrays.stream(SessionType.values())
                    .collect(Collectors.toMap(type -> type.type, type -> type));
        }

        private final String type;

        SessionType(String type) {
            this.type = type;
        }

        @JsonProperty
        public String getType() {
            return type;
        }

        @JsonCreator
        @NonNull
        public static SessionType getType(String type) {
            return SESSION_TYPE_MAP
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equalsIgnoreCase(type))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalArgumentException("Not found session type for: " + type)
                    );
        }


        @Override
        public String toString() {
            return "SessionType{" +
                    ", description='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "token='" + token + '\'' +
                ", sessionType=" + sessionType +
                ", clientId=" + userId +
                ", expDate=" + expDateTime +
                '}';
    }


    public static class Builder {
        private String token;
        private SessionType sessionType;
        private Long userId;
        private Instant expDateTime;

        private Builder() {
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setSessionType(SessionType sessionType) {
            this.sessionType = sessionType;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setExpDateTime(Instant expDateTime) {
            this.expDateTime = expDateTime;
            return this;
        }

        public Builder of(SessionData sessionData) {
            this.token = sessionData.token;
            this.sessionType = sessionData.sessionType;
            this.userId = sessionData.userId;
            this.expDateTime = sessionData.expDateTime;
            return this;
        }

        public SessionData build() {
            return new SessionData(this);
        }
    }
}
