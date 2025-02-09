package ru.sw.stock_price_monitoring.service.request.strategy;

import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.util.DataContainer;
import ru.sw.stock_price_monitoring.util.DateRange;

public interface RequestStockDataStrategy {
    DataContainer getStockData(Stock stock, DateRange dateRange);

    boolean applicability(int i);
}
