package ru.sw.stock_price_monitoring.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.util.DataContainer;
import ru.sw.stock_price_monitoring.util.DateRange;
import ru.sw.stock_price_monitoring.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RequestStockDataService {
    private final RequestStockDataContext requestStockData;

    public DataContainer getStockData(Stock stock, List<LocalDate> datesForRequest) {
        List<DateRange> dateRanges = DateUtil.generateDateRangesOf(datesForRequest);
        DataContainer container = new DataContainer();

        for (DateRange dateRange : dateRanges) {
            container.add(requestStockData.getStockData(stock, dateRange, dateRange.getSize()));
        }
        return container;
    }
}
