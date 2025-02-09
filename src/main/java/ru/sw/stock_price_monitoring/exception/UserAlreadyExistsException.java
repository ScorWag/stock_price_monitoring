package ru.sw.stock_price_monitoring.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
