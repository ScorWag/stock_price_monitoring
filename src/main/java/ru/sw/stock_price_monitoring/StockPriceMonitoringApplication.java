package ru.sw.stock_price_monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan("ru.sw.stock_price_monitoring.config.properties")
public class StockPriceMonitoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockPriceMonitoringApplication.class, args);
    }
}
