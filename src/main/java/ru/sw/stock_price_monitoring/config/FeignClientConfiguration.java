package ru.sw.stock_price_monitoring.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sw.stock_price_monitoring.config.properties.SecretProperties;

import static ru.sw.stock_price_monitoring.constants.AuthenticationConstants.*;

@RequiredArgsConstructor
@Component
public class FeignClientConfiguration  {

    private final SecretProperties secretProperties;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(
                HEADER_AUTHORIZATION,
                BEARER_PREFIX + secretProperties.getPolygonApiKey()
        );
    }
}
