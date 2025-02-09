package ru.sw.stock_price_monitoring.config;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sw.stock_price_monitoring.dto.error.UserAlreadyExistsError;
import ru.sw.stock_price_monitoring.dto.error.ValidationErrorDto;
import ru.sw.stock_price_monitoring.exception.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserAlreadyExistsError> userAlreadyExistsErrorHandler(
            UserAlreadyExistsException ex) {

        return new ResponseEntity<>(new UserAlreadyExistsError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ValidationErrorDto> validationErrorHandler(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        List<ObjectError> objectErrors = ex.getAllErrors();

        for (ObjectError objectError : objectErrors) {
            if (objectError instanceof FieldError fieldError) {
                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else errors.add(objectError.getDefaultMessage());
        }

        return new ResponseEntity<>(new ValidationErrorDto(errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({FeignException.TooManyRequests.class, FeignException.Unauthorized.class})
    public ResponseEntity<String> feignExceptionHandler(FeignException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
