package ru.sw.stock_price_monitoring.service.prepare.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.StockData;
import ru.sw.stock_price_monitoring.entity.User;
import ru.sw.stock_price_monitoring.service.StockDataService;
import ru.sw.stock_price_monitoring.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilterAndSaveDatabaseSavedStockData extends PrepareRequestForClient {
    private final StockDataService stockDataService;
    private final UserService userService;

    @Override
    public PrepareType getType() {
        return PrepareType.FILTER_AND_SAVE_DB_DATA;
    }

    @Override
    public boolean prepare(User user, Stock stock, List<LocalDate> dates) {
        dates.sort(LocalDate::compareTo);
        if (!dates.isEmpty()) {
            LocalDate from = dates.get(0);
            LocalDate to = dates.get(dates.size() - 1);

            List<StockData> databaseStockDataList = stockDataService.getStockDataByStockAndDateRange(stock, from, to);

            List<StockData> savedInDatabase = databaseStockDataList.stream()
                    .filter(stockData -> dates.contains(stockData.getDate()))
                    .toList();

            userService.addStockDataList(user, savedInDatabase);

            dates.removeAll(savedInDatabase.stream().map(StockData::getDate).toList());

            boolean isRemoveData = dates.removeAll(savedInDatabase.stream().map(StockData::getDate).toList());

            return next.map(prepare -> prepare.prepare(user, stock, dates) || isRemoveData).orElse(isRemoveData);
        }

        return false;
    }
}
