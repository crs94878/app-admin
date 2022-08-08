package ru.sshell.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
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
     * Метод добавляет к запросу хеадер авторизация (в котором есть токен) в зависимости от необходимости
     * @param request   запрос
     * @param body      тело запроса
     * @param execution контекст http запроса
     * @return          ответ сервера
     * @throws IOException ошибка вывода
     */
    private ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        Arrays.stream(stackTraces).forEach(stackTraceElement -> findChekcAuthAnnotation(stackTraceElement, request));
        return execution.execute(request, body);
    }

    /**
     * Поиск аннотации, где нужно опустить хеадер авторизации и оттправить запрос без него
     * @param stackTraceElement стек вызовов
     * @param request запрос
     */
    private void findChekcAuthAnnotation(StackTraceElement stackTraceElement, HttpRequest request) {
        if (request.getURI().toString().contains("checkin")) {
            return;
        }
        try {
            Method[] methods = Class.forName(stackTraceElement.getClassName()).getDeclaredMethods();
            for (Method method : methods) {
                CheckAuthorisation authorisation = method.getDeclaredAnnotation(CheckAuthorisation.class);
                setAuthHeaderIfNeed(authorisation, request);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("Error while add headers", e);
        }
    }

    /**
     * Установка хеадера авторизации если есть необходимость
     * @param authorisation аннотация авотиризации
     * @param request запрос
     */
    private void setAuthHeaderIfNeed(CheckAuthorisation authorisation, HttpRequest request) {
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

    /**
     * Сгенерировать URL с парамтерами для get запроса
     * @param key        ключ для подстановки в адрес строки
     * @param param      парамтеры запроса
     * @param requestUrl конечная точка запроса
     * @return URL с парамтерами для get запроса
     */
    UriComponents buildUrl(Object key, Object param, String requestUrl) {
        return UriComponentsBuilder.fromHttpUrl(requestUrl).queryParam(key.toString(), param.toString()).build();
    }

    /**
     * Функция вызывает метод {@link RestTemplate#postForObject} для отправки post запроса
     * @param url           конечный адрес запроса
     * @param request       запрос
     * @param responseType  тип ответа
     * @param <T>           параметризированный тип, для типа ответа
     * @return ответ
     */
    <T> T postRequest(String url, Object request, Class<T> responseType) {
        return restTemplate.postForObject(url, request, responseType);
    }

    /**
     * Функция вызывает метод {@link RestTemplate#getForObject} для отправки get запроса
     * @param url           конечный адрес запроса
     * @param responseType  тип ответа
     * @param <T>           параметризированный тип, для типа ответа
     * @return ответ
     */
    <T> T getRequest(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

}
