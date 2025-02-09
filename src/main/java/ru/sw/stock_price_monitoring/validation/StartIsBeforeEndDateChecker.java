package ru.sw.stock_price_monitoring.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class StartIsBeforeEndDateChecker implements ConstraintValidator<CheckDate, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public void initialize(CheckDate annotation) {
        field = annotation.field();
        fieldMatch = annotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        if (fieldValue != null && fieldMatchValue != null) {
            LocalDate startDate;
            LocalDate endDate;

            try {
                startDate = LocalDate.parse(fieldValue.toString());
                endDate = LocalDate.parse(fieldMatchValue.toString());
            } catch (DateTimeParseException e) {
                return false;
            }

            boolean isValid = startDate.isBefore(endDate);
            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("{validation-check-date}")
                        .addConstraintViolation();
            }

            return isValid;
        }
        return false;
    }
}
