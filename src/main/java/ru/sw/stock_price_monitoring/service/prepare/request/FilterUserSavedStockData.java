package ru.sw.stock_price_monitoring.service.prepare.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.StockData;
import ru.sw.stock_price_monitoring.entity.User;
import ru.sw.stock_price_monitoring.service.StockDataService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilterUserSavedStockData extends PrepareRequestForClient {
    private final StockDataService stockDataService;

    @Override
    public PrepareType getType() {
        return PrepareType.FILTER_USER_DATA;
    }

    @Override
    public boolean prepare(User user, Stock stock, List<LocalDate> dates) {
        List<StockData> userStockData = stockDataService.getStockDataByUserAndStock(user, stock);
        List<LocalDate> userStockDataDates = new ArrayList<>();
        if (!userStockData.isEmpty()) {
            userStockDataDates = userStockData.stream().map(StockData::getDate).toList();
        }

        boolean isRemoveData = dates.removeAll(userStockDataDates);
        return next.map(prepare -> prepare.prepare(user, stock, dates) || isRemoveData).orElse(isRemoveData);
    }
}
