package com.example.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "spring.mail")
@Data
@Configuration
public class EmailProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private Boolean auth;
    private Boolean starttlsEnable;
    private Boolean starttlsRequired;
    private int timeout;
    private int connectiontimeout;
    private Boolean debug;
    private String type = "smtp";
    private String emailFrom = "SergeiLisovoi22@yandex.ru";
    private String emailTo = "SergeiLisovoi22@yandex.ru";
}
