package ru.sshell.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import ru.sshell.model.SessionData;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Основной сервис для отправки Rest запросов на сервис
 */
public class RootRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootRestService.class);

    protected SessionData sessionData;
    protected RestTemplate restTemplate;

    RootRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        restTemplate.getInterceptors().add(this::intercept);
    }

    /**
     * Метод добавляет к запросу хеадер авторизация (в котором енсть токен) в зависимости от необходимости
     * @param request   запрос
     * @param body      тело запроса
     * @param execution контекст http запроса
     * @return          ответ сервера
     * @throws IOException ошибка вывода
     */
    private ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        Arrays.stream(stackTraces).forEach(stackTraceElement -> findAutAnnotation(stackTraceElement, request));
        return execution.execute(request, body);
    }

    /**
     * Поиск аннотации, где нужно опустить хеадер авторизации и оттправить запрос без него
     * @param stackTraceElement стек вызовов
     * @param request запрос
     */
    private void findAutAnnotation(StackTraceElement stackTraceElement, HttpRequest request) {
        if (!request.getURI().toString().contains("checkin")) {
            try {
                Method[] methods = Class.forName(stackTraceElement.getClassName()).getDeclaredMethods();
                for (Method method : methods) {
                    CheckAuthorisation authorisation = method.getDeclaredAnnotation(CheckAuthorisation.class);
                    setHeaderIfNeed(authorisation, request);
                }
            } catch (ClassNotFoundException e) {
                LOGGER.error("Error while add headers", e);
            }
        }
    }

    /**
     * Установка хеадера авторизации если есть необходимость
     * @param authorisation аннотация авотиризации
     * @param request запрос
     */
    private void setHeaderIfNeed(CheckAuthorisation authorisation, HttpRequest request) {
        if (authorisation != null) {
            if (authorisation.disable()) {
                return;
            }
        }
        String token = "";
        if (sessionData != null) {
            token = sessionData.getToken();
        }
        request.getHeaders().set(HttpHeaders.AUTHORIZATION, token);
    }
}
