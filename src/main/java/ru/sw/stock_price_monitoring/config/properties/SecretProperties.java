package ru.sw.stock_price_monitoring.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "spring.secrets")
@Configuration
public class SecretProperties {
    @NotBlank
    private String jwtSecretKey;

    @NotBlank
    private String polygonApiKey;
}

