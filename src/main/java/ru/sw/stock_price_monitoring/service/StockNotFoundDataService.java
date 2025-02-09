package ru.sw.stock_price_monitoring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.dao.StockNotFoundDataRepository;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.StockNotFoundData;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockNotFoundDataService {
    private final StockNotFoundDataRepository stockNotFoundDataRepository;

    public List<LocalDate> findRequestDatesByStock(Stock stock, List<LocalDate> dates) {
        return stockNotFoundDataRepository.findByStockAndDateIn(stock, dates).stream()
                .map(StockNotFoundData::getDate).toList();
    }

    public void saveAll(List<StockNotFoundData> stockNotFoundDataList) {
        stockNotFoundDataRepository.saveAll(stockNotFoundDataList);
    }
}
