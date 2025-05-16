package ru.t1.logging.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.t1.logging.aspect.LoggingAspect;

@Configuration()
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(prefix = "logging.aspect", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LoggingConfiguration {

    @Bean
    public LoggingAspect loggingAspect(LoggingProperties properties) {
        LoggingAspect aspect = new LoggingAspect();
        aspect.setLogLevel(properties.getLevel().toLowerCase());
        return aspect;
    }
}
