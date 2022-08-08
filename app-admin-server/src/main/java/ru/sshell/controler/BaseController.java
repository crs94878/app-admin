package ru.sshell.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sshell.exception.ClientAuthorizationException;
import ru.sshell.exception.ResourceBlockedException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class BaseController {

    private static final Logger LOGER = LoggerFactory.getLogger(BaseController.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @ExceptionHandler(ClientAuthorizationException.class)
    public void illegalClientAuthAction(HttpServletResponse response, ClientAuthorizationException ex) throws IOException {
        LOGER.error("Service handle exception", ex);
        response.setStatus(ex.getStatusCode().value());
        OBJECT_MAPPER.writeValue(response.getOutputStream(), ex.getMessage());
    }

    @ExceptionHandler(ResourceBlockedException.class)
    public void illegalAdminAuthAction(HttpServletResponse response, ClientAuthorizationException ex) throws IOException {
        LOGER.error("Service handle exception", ex);
        response.setStatus(ex.getStatusCode().value());
        OBJECT_MAPPER.writeValue(response.getOutputStream(), ex.getMessage());
    }
}
