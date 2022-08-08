package ru.sshell.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sshell.service.AuthorizationService;

import java.util.List;

/**
 * @author belka
 * Date: 2020.11.19
 */
@Configuration
public class WebConfiguration {

    @Value("#{'${urls.not.requiring.auth}'.split(',')}")
    private List<String> urlsNotRequiringAuth;

    @Autowired
    private AuthorizationService authorizationService;

    @Bean
    public WebFilter webFilter() {
        return new WebFilter(urlsNotRequiringAuth, authorizationService);
    }
}
