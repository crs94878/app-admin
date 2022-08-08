package ru.sshell.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

/**
 * @author sshell
 * Date: 24.03.2020
 */
public class ProcessBrokenException extends HttpServerErrorException {

    public ProcessBrokenException(HttpStatus httpStatus, String responseText) {
        super(httpStatus, responseText);
    }
}
