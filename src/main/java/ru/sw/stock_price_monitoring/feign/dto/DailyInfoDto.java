package ru.sw.stock_price_monitoring.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ru.sw.stock_price_monitoring.config.LocalDateJsonDeserializer;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DailyInfoDto {
    @JsonProperty("c")
    private BigDecimal close;

    @JsonProperty("h")
    private BigDecimal high;

    @JsonProperty("l")
    private BigDecimal low;

    @JsonProperty("n")
    private Integer number;

    @JsonProperty("o")
    private BigDecimal open;

    @JsonProperty("t")
    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    private LocalDate date;

    @JsonProperty("v")
    private Long volume;

    @JsonProperty("vw")
    private Long volumeWeight;
}
