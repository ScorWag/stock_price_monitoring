package ru.sw.stock_price_monitoring.service.prepare.request;

import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class PrepareRequestForClient {
    protected Optional<PrepareRequestForClient> next = Optional.empty();

    public void setNext(PrepareRequestForClient next) {
        this.next = Optional.of(next);
    }

    public abstract PrepareType getType();

    public abstract boolean prepare(User user, Stock stock, List<LocalDate> dates);
}
