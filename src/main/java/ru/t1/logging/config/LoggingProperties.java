package ru.t1.logging.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "logging.aspect")
@Setter
@Getter
public class LoggingProperties {

    private boolean enabled = true;
    private String level = "INFO";
}
