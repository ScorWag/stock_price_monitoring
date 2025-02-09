package ru.sw.stock_price_monitoring.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BarsDto {
    private Boolean adjusted;

    @JsonProperty("next_url")
    private String nextUrl;

    private Integer queryCount;

    @JsonProperty("request_id")
    private String requestId;

    private List<DailyInfoDto> results;

    private Integer resultsCount;

    private String status;

    private String ticker;
}
