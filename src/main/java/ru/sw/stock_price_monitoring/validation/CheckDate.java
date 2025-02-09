package ru.sw.stock_price_monitoring.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = StartIsBeforeEndDateChecker.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface CheckDate {
    String message() default "{validation-check-date-default-message}";
    String field();
    String fieldMatch();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
