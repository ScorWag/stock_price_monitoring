package ru.sw.stock_price_monitoring.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.security")
@Configuration
public class SecurityProperties {

    private Map<String, String> jwt;

    public Long getAccessTokenExpiration() {
        return Long.parseLong(jwt.get("access_token_expiration"));
    }

    public Long getRefreshTokenExpiration() {
        return Long.parseLong(jwt.get("refresh_token_expiration"));
    }
}
