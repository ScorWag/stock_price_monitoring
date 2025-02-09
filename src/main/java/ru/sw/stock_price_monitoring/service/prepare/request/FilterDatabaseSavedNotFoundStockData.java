package ru.sw.stock_price_monitoring.service.prepare.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.User;
import ru.sw.stock_price_monitoring.service.StockNotFoundDataService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilterDatabaseSavedNotFoundStockData extends PrepareRequestForClient{

    private final StockNotFoundDataService stockNotFoundDataService;

    @Override
    public PrepareType getType() {
        return PrepareType.FILTER_NOT_FOUND_DB_DATA;
    }

    @Override
    public boolean prepare(User user, Stock stock, List<LocalDate> dates) {
        boolean isRemoveData = dates.removeAll(
                stockNotFoundDataService.findRequestDatesByStock(stock, dates)
        );

        return next.map(prepare -> prepare.prepare(user, stock, dates) || isRemoveData).orElse(isRemoveData);
    }
}
