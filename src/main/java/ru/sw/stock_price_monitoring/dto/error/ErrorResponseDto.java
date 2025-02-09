package ru.sw.stock_price_monitoring.dto.error;

import lombok.Builder;

@Builder
public record ErrorResponseDto(String message) {
}
