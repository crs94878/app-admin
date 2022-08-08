package ru.sshell.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class ResourceBlockedException extends HttpServerErrorException {
    public ResourceBlockedException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
