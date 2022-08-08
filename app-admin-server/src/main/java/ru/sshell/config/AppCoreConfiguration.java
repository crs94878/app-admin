package ru.sshell.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Главный конфигурационный класс
 * @author belka
 * Date: 2020.11.19
 */
@Configuration
@Import(value = {
        DaoConfiguration.class,
        WebConfiguration.class,
        DbConfiguration.class
})
public class AppCoreConfiguration {
}
