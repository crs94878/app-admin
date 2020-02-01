package ru.sshell.service.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, которой можно пометить метод, где не нужно использовать метод авторизации в запросе на сервер.
 * Header авторизации не будет подставлен в запрос
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthorisation {
    boolean disable() default false;
}
