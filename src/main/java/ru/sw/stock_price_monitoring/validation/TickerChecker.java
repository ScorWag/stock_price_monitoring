package ru.sw.stock_price_monitoring.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sw.stock_price_monitoring.service.StockService;

@RequiredArgsConstructor
@Component
public class TickerChecker implements ConstraintValidator<CheckTicker, String> {
    private final StockService stockService;

    @Override
    public void initialize(CheckTicker constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return stockService.existsByTicker(value);
    }
}
