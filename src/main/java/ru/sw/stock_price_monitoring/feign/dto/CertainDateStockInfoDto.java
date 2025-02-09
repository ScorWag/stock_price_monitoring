package ru.sw.stock_price_monitoring.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CertainDateStockInfoDto {
    private BigDecimal afterHours;

    private BigDecimal close;

    @JsonProperty("from")
    private LocalDate date;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal open;

    private BigDecimal preMarket;

    private String status;

    private String symbol;

    private Long volume;
}
