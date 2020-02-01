package ru.sshell.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Класс утилита для гененрации токена
 */
public class TokenGenerator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Base64.Encoder BASE_64_ENCODER = Base64.getUrlEncoder();

    /**
     * Генерация токена сессии
     * @return токен сессии
     */
    public static String generateToken() {
        byte[] randomBytes = new byte[24];
        SECURE_RANDOM.nextBytes(randomBytes);
        return BASE_64_ENCODER.encodeToString(randomBytes);
    }
}
