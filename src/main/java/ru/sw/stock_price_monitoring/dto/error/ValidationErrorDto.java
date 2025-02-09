package ru.sw.stock_price_monitoring.dto.error;

import java.util.List;

public record ValidationErrorDto(List<String> messages) {
}
