package ru.sshell.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * ROOT Конфигурация приложения
 * @author sshell
 * Date: 15.02.2020
 */
@Configuration
@Import(value = AppCoreConfiguration.class)
@PropertySource(value = "classpath:application.properties")
public class RootConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
