package ru.sshell.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class ClientAuthorizationException extends HttpServerErrorException {
    public ClientAuthorizationException(HttpStatus statusCode, String message) {
        super(statusCode, message);
    }
}
