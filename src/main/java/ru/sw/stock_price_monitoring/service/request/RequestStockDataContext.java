package ru.sw.stock_price_monitoring.service.request;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.service.request.strategy.RequestStockDataStrategy;
import ru.sw.stock_price_monitoring.util.DataContainer;
import ru.sw.stock_price_monitoring.util.DateRange;

import java.util.List;

@AllArgsConstructor
@Setter
@Component
public class RequestStockDataContext {

    private List<RequestStockDataStrategy> strategies;

    public DataContainer getStockData(Stock stock, DateRange dateRange, int applyStrategy)  {
        for (RequestStockDataStrategy strategy : strategies) {
            if (strategy.applicability(applyStrategy)) {
                return strategy.getStockData(stock, dateRange);
            }
        }
        return new DataContainer();
    }
}
