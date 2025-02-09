package ru.sw.stock_price_monitoring.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = TickerChecker.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface CheckTicker {

    String message() default "{validation-ticker}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
