package ru.sshell.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import ru.sshell.model.SessionData;
import ru.sshell.service.AuthorizationService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Класс конфигурация для сервлета, фильтр для обработки входящих запросов на сервер
 */
public class WebFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebFilter.class);
    private static final String CONTENT_TYPE = "application/json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final List<String> urlsNotRequiringAuth;

    private final AuthorizationService authorizationService;

    public WebFilter(List<String> urlsNotRequiringAuth, AuthorizationService authorizationService) {
        this.urlsNotRequiringAuth = urlsNotRequiringAuth;
        this.authorizationService = authorizationService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) {
       try {
           HttpServletRequest request = (HttpServletRequest) servletRequest;

           if (checkRequestSkippedUrl(request, servletResponse, filterChain)) {
               return;
           }
           String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
           if (!checkTokenValid(request, servletResponse, authToken)) {
               return;
           }
           List<SessionData> sessionDataList = authorizationService.loadSessionDataByToken(authToken);
           if (!checkSessionExist(servletResponse, sessionDataList, authToken)) {
               return;
           }
           filterChain.doFilter(request, servletResponse);
       } catch (Exception ex) {
           LOGGER.error("Error process thread session filter", ex);
       } finally {
           AuthHolder.removeAuthData();
       }
    }

    private boolean checkRequestSkippedUrl(HttpServletRequest request, ServletResponse servletResponse,
                                           FilterChain filterChain) throws IOException, ServletException {
        boolean findUrlSkiped = urlsNotRequiringAuth.stream().anyMatch(url -> request.getRequestURI().startsWith(url));
            // не использовать авторизацию для заданных URL
        if (findUrlSkiped) {
            LOGGER.debug("skipped auth for URL {}", request.getRequestURI());
            filterChain.doFilter(request, servletResponse);
        }
            return findUrlSkiped;
    }

    private boolean checkTokenValid(HttpServletRequest request, ServletResponse servletResponse,
                                    String authToken) throws IOException {
        if (StringUtils.isBlank(authToken)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                return checkCookiesToken(cookies, servletResponse);
            }
        }
        return true;
    }

    private boolean checkCookiesToken(Cookie[] cookies, ServletResponse servletResponse) throws IOException {
        Optional<Cookie> coockie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token")).findFirst();
        if (coockie.isPresent()) {
            if (StringUtils.isBlank(coockie.get().getValue())) {
                LOGGER.debug("Session token is null");
                sendErrorResponse((HttpServletResponse) servletResponse,
                        "Session token is null", HttpStatus.UNAUTHORIZED);
                return false;
            }
        }
        return true;
    }

    private boolean checkSessionExist(ServletResponse servletResponse,
                                      List<SessionData> sessionDataList, String authToken) throws IOException {
        if (CollectionUtils.isNotEmpty(sessionDataList)) {
            SessionData sessionData = sessionDataList.get(0);
            return checkSessionRuleValid(servletResponse, sessionData, authToken);
        } else {
            // сессия не найдена, значит возвращаем ошибку авторизации
            LOGGER.debug("Session by token {} is not found", authToken);
            sendErrorResponse((HttpServletResponse) servletResponse,
                    "Session not found. Auth token is invalid", HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

    private boolean checkSessionRuleValid(ServletResponse servletResponse,
                                          SessionData sessionData, String authToken) throws IOException {
        switch (sessionData.getSessionType()) {
            case ADMIN:
                authorizationService.addOrUpdateSessionData(sessionData);
                AuthHolder.setAuthData(sessionData);
                LOGGER.debug("Request by authorization session: {}", sessionData);
                return true;
            case CLIENT:
            default:
                LOGGER.debug("Session by token {} is Expired", authToken);
                sendErrorResponse((HttpServletResponse) servletResponse, "Session token is Expired",
                        HttpStatus.UNAUTHORIZED);
                return false;
        }
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   String message,
                                   HttpStatus httpStatus) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(httpStatus.value());
        LOGGER.error("Send error response with status: {} and message: {}", httpStatus, message);
        objectMapper.writeValue(response.getOutputStream(), message);
    }
}
